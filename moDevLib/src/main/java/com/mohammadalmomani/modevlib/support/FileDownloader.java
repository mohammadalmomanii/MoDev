package com.mohammadalmomani.modevlib.support;

import static androidx.core.content.ContextCompat.registerReceiver;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.mohammadalmomani.modevlib.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileDownloader extends BroadcastReceiver {
    public static Context context;
    String fileName;
    long downloadID;


    public FileDownloader(Activity activity) {
        FileDownloader.context = activity;
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                registerReceiver(activity,FileDownloader.this, filter, ContextCompat.RECEIVER_EXPORTED);
            }
        });

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        if (downloadID == id) {
            AppHelper.openFile(context, fileName);
        }
    }

   // Append extension

        public void downloadFile(String url, String name) {
            String extension = getFileExtension(url);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            fileName = name + "_" + timestamp + extension;
            Uri uri = Uri.parse(url);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
        if (file.exists()) { // if your file exit
            file.delete();
        }
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(context.getString(R.string.download) + " " + fileName);
        request.setDescription(context.getString(R.string.update));
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        downloadID = manager.enqueue(request);


    }



    private String getFileExtension(String url) {
        try {
            return url.substring(url.lastIndexOf(".")); // Extract file extension
        } catch (Exception e) {
            e.printStackTrace();
            return ""; // Return empty string if extraction fails
        }
    }

}
