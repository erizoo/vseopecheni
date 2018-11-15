package ru.vseopecheni.app.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import ru.vseopecheni.app.R;
import ru.vseopecheni.app.ui.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {

    String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + new Date().toString());

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notification = new NotificationCompat.Builder(context)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.stol5))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setSound(alarmSound)
                .setContentTitle(intent.getStringExtra("TITLE"))
                .setContentText(intent.getStringExtra("CONTENT"))
                .build();

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (intent.getStringExtra("CONTENT").equals("1")){
            Objects.requireNonNull(notificationManager).notify(101, notification);
        }
        if (intent.getStringExtra("CONTENT").equals("2")){
            Objects.requireNonNull(notificationManager).notify(102, notification);
        }
        if (intent.getStringExtra("CONTENT").equals("3")){
            Objects.requireNonNull(notificationManager).notify(103, notification);
        }
        if (intent.getStringExtra("CONTENT").equals("4")){
            Objects.requireNonNull(notificationManager).notify(104, notification);
        }
        if (intent.getStringExtra("CONTENT").equals("5")){
            Objects.requireNonNull(notificationManager).notify(105, notification);
        }

    }
}
