package ru.vseopecheni.app.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Objects;

public class AlarmBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Objects.equals(intent.getAction(), "android.intent.action.BOOT_COMPLETED")) {
            //only enabling one type of notifications for demo purposes
            NotificationHelper.scheduleRepeatingElapsedNotification(context);
        }
    }
}
