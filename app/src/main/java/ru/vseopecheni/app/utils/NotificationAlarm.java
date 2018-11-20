package ru.vseopecheni.app.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import java.util.Calendar;
import java.util.Objects;

import static android.content.Context.ALARM_SERVICE;

public class NotificationAlarm {

    public static void scheduleNotification(Context context, Calendar time, String title, String content, int idAlarm) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("TITLE", title);
        intent.putExtra("CONTENT", content);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, idAlarm,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public static void cancelReminder(Context context, int id) {
        ComponentName receiver = new ComponentName(context, AlarmReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        Intent intent1 = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Objects.requireNonNull(am).cancel(pendingIntent);
        pendingIntent.cancel();
    }
}
