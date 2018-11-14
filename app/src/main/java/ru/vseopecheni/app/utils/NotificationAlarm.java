package ru.vseopecheni.app.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Objects;

public class NotificationAlarm {

    public static void SheduleNotification(Context context, long time, String title, String content, int idAlarm) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("TITLE", title);
        intent.putExtra("CONTENT", content);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, idAlarm,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Objects.requireNonNull(alarmManager).setRepeating(AlarmManager.RTC_WAKEUP,
                time, AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
