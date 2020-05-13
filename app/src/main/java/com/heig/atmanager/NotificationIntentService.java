package com.heig.atmanager;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;

public class NotificationIntentService extends IntentService {

    private static final int NOTIFICATION_ID = 90;

    public NotificationIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentTitle(intent.getStringExtra("title"));
        builder.setContentText("Bla");
        builder.setSmallIcon(R.drawable.atm_logo);

        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);
    }
}
