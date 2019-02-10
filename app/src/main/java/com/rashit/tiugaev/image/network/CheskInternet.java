package com.rashit.tiugaev.image.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheskInternet {
    private static ConnectivityManager cm;
    private static NetworkInfo netInfo;

    public static boolean chekInternet (Context context){
        cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
