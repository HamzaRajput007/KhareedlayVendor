package com.example.khareedlayvendor;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class NotificationClass extends Application {

    public static final String NOTIFICATION = "notification";
    NotificationChannel notificationChannel = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();

        generateNotifications();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void generateNotifications() {
        if (notificationChannel == null) {
            notificationChannel = new NotificationChannel(NOTIFICATION, "order notification",
                    NotificationManager.IMPORTANCE_HIGH);
        }
        notificationChannel.setDescription("New Order placed");

        NotificationManager manager = getSystemService(NotificationManager.class);
        assert manager != null;
        manager.createNotificationChannel(notificationChannel);
    }
}
