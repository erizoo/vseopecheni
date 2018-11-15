package ru.vseopecheni.app.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import java.util.Calendar;
import java.util.Objects;

public class NotificationAlarm {

    public static void SheduleNotification(Context context, Calendar time, String title, String content, int idAlarm) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("TITLE", title);
        intent.putExtra("CONTENT", content);
        Calendar calendar = Calendar.getInstance();
        // Enable a receiver
        if (time.before(calendar))
            time.add(Calendar.DATE, 1);

        ComponentName receiver = new ComponentName(context, AlarmReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, idAlarm,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Objects.requireNonNull(alarmManager).setRepeating(AlarmManager.RTC_WAKEUP,
                time.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
