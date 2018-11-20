package ru.vseopecheni.app.utils;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.Date;

import ru.vseopecheni.app.R;

public class AlarmReceiver extends BroadcastReceiver {

    String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + new Date().toString());

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notification = new NotificationCompat.Builder(context, "Все о печени")
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.stol5))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setSound(alarmSound)
                .setContentTitle(intent.getStringExtra("TITLE"))
                .setContentText(intent.getStringExtra("CONTENT"))
                .build();

        NotificationManagerCompat.from(context).notify((int) System.currentTimeMillis(), notification);
    }
}
