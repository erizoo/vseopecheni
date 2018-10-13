package ru.vseopecheni.app.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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

}