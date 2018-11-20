package ru.vseopecheni.app.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public interface Constant {

    String BASE_URI = "https://vseopecheni.ru/";
    String TAG = "READ";

    static ProgressDialog showLoadingDialog(Context context) {
        if (context != null) {
            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Идет загрузка...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            return progressDialog;
        }

        return null;
    }

    static ProgressDialog showLoadingDialog(Context context, String title) {
        if (context != null) {
            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Идет загрузка..." + " " + title);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            return progressDialog;
        }

        return null;
    }

    static boolean isInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) Objects.requireNonNull(context).getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else {
            return false;
        }
    }

    static void saveToSharedPreference(String id, String content, Activity activity) {
        SharedPreferences sharedPreferences = activity.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString(id, content);
        ed.apply();
    }

    static String getFromSharedPreference(String id, Activity activity) {
        SharedPreferences sharedPreferences = activity.getPreferences(MODE_PRIVATE);
        return sharedPreferences.getString(id, "");
    }

    static void saveToSharedPreferenceAlarm(String id, int content, Activity activity) {
        SharedPreferences sharedPreferences = activity.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putInt(id, content);
        ed.apply();
    }

    static int getFromSharedPreferenceAlarm(String id, Activity activity) {
        SharedPreferences sharedPreferences = activity.getPreferences(MODE_PRIVATE);
        return sharedPreferences.getInt(id, 106);
    }

}
