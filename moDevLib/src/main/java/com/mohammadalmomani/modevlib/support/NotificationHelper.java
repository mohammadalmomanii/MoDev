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
                Build.BRAND.toLowerCase(Locale.ENGLISH),
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
