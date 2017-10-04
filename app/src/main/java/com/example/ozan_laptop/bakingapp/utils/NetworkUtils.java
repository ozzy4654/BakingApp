package com.example.ozan_laptop.bakingapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by ozan-laptop on 9/28/2017.
 */

public class NetworkUtils {

    /**
     * This method allow the app to check for network changes
     * so in the event of Network/wifi is down or in airplane mode
     * the app will not crash
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }



}
