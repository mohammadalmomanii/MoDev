package com.mohammadalmomani.modev.support;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import com.mohammadalmomani.modev.R;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationHelper extends Service {


    static private NotificationChannel channel;


    public NotificationHelper() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(Build.BRAND.toLowerCase(Locale.ENGLISH) + "",
                    Build.BRAND.toLowerCase(Locale.ENGLISH), NotificationManager.IMPORTANCE_HIGH);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {

        createChannel();
        // cancel if service is  already existed
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                //Toast.makeText(getApplicationContext(), "Hello World", Toast.LENGTH_SHORT).show();
                notifyThis(getApplicationContext(), "test", "sss", null, 20);

            }
        };
//        timer.schedule(timerTask, 0, 3000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @SuppressLint("MissingPermission")
    static public void notifyThis(Context context, String title, String message, Intent intent, int notificationId) {
        ///////////////////////////////////
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);

        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(context, channel.getId())
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setLargeIcon(largeIcon)
                    .setOngoing(false)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        if (intent != null) {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
            builder.setContentIntent(pendingIntent);
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());


        ///////////////////////////////////////
    }
}
