package com.heig.atmanager.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;

import com.heig.atmanager.BuildConfig;
import com.heig.atmanager.R;

public class NotificationUtils extends ContextWrapper {

    private NotificationManager mManager;
    private Context mContext;

    public static final String CHANNEL_ID = BuildConfig.APPLICATION_ID;
    public static final String CHANNEL_NAME = "ATM CHANNEL";

    public NotificationUtils(Context base) {
        super(base);
        mContext = base;
        createChannel();
    }

    public void createChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

        channel.enableLights(true);

        channel.enableVibration(true);

        channel.setLightColor(Color.BLUE);

        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel);
    }


    public Notification.Builder getChannelNotification(String title, String body) {
        return new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.atm_logo)
                .setAutoCancel(true);
    }

    public void scheduleNotification(Notification notification, long time) {
        Intent notificationIntent = new Intent(mContext, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }
}
