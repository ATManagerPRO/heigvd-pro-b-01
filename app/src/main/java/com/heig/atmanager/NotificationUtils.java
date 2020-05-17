package com.heig.atmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;

public class NotificationUtils extends ContextWrapper {

    private NotificationManager mManager;

    public static final String CHANNEL_ID = BuildConfig.APPLICATION_ID;
    public static final String CHANNEL_NAME = "ATM CHANNEL";

    public NotificationUtils(Context base) {
        super(base);
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

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }
}
