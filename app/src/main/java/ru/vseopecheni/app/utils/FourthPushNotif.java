package ru.vseopecheni.app.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ru.vseopecheni.app.ui.MainActivity;

public class FourthPushNotif extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("FourthPushNotif", "onReceive: ");
        //Trigger the notification
        NotificationScheduler.showNotification(context, MainActivity.class,
                "Время приема пищи", "4");
    }
}
