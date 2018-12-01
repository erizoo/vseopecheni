package ru.vseopecheni.app.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.Date;

import ru.vseopecheni.app.R;

public class AlarmReceiver extends BroadcastReceiver {

    private String TAG = "AlarmReceiver";

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
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel("my_channel_01", "my_channel", importance);
            mChannel.setDescription("This is my channel");
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(true);

            if (notificationManager != null) {

                notificationManager.createNotificationChannel(mChannel);
                notificationManager.notify((int) System.currentTimeMillis(), notification);
            }
        } else {
            NotificationManagerCompat.from(context).notify((int) System.currentTimeMillis(), notification);
        }
    }
}
