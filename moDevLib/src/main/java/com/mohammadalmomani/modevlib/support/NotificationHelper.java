// NotificationHelper.java
package com.mohammadalmomani.modevlib.support;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.mohammadalmomani.modevlib.R;

import java.util.Locale;

public class NotificationHelper extends AppCompatActivity {
    /**
     * NotificationHelper is a utility class for creating and displaying notifications in Android.
     * It simplifies the process of setting up notifications with a dedicated notification channel.
     * <p>
     * Usage:
     * <p>
     * 1. Initialize NotificationHelper:
     *    NotificationHelper notificationHelper = new NotificationHelper(context);
     * <p>
     * 2. Create a Notification:
     *    NotificationCompat.Builder builder = notificationHelper.createNotification()
     *        .setSmallIcon(R.drawable.ic_notification) // Set notification icon
     *        .setContentTitle("Notification Title") // Set title
     *        .setContentText("This is the notification message.") // Set message
     *        .setPriority(NotificationCompat.PRIORITY_HIGH);
     * <p>
     * 3. Show the Notification:
     *    notificationHelper.showNotification(1001); // Pass a unique notification ID
     * <p>
     * Notes:
     * - Ensure the app has notification permissions on Android 13+.
     * - The notification channel is created automatically upon initialization.
     */

    private final String CHANNEL_ID;
    private final Context context;
    private NotificationCompat.Builder builder;


    public NotificationHelper(Context context) {
        this.context = context;
        CHANNEL_ID = context.getString(R.string.app_name);
        createChannel();

    }


    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                AppHelper.getAppName(context).toLowerCase(Locale.ENGLISH),
                NotificationManager.IMPORTANCE_HIGH);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }


    public NotificationCompat.Builder createNotification() {
        builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        return builder;
    }

    @SuppressLint("MissingPermission")
    public void showNotification(int notificationId) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());

    }


//    public void notifyThis(String title, String message, Intent intent, int notificationId) {
//        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
//
//        NotificationCompat.Builder builder = null;
//        builder = new NotificationCompat.Builder(context, CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setAutoCancel(true)
//                .setLargeIcon(largeIcon)
//                .setOngoing(false)
//                .setPriority(NotificationCompat.PRIORITY_HIGH);
//
//        if (intent != null) {
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
//            builder.setContentIntent(pendingIntent);
//        }
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//        notificationManager.notify(notificationId, builder.build());
//    }
}
