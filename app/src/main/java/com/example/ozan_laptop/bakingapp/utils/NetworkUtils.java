package com.example.ozan_laptop.bakingapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.example.ozan_laptop.bakingapp.data.models.Ingredient;
import com.example.ozan_laptop.bakingapp.data.models.Recipe;
import com.example.ozan_laptop.bakingapp.data.models.Step;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

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
    private static final Gson mGson = new Gson();

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    public static String convertToJson(Object mList) {
        return mGson.toJson(mList);
    }

    public static List<?> convertToObject(String mList,boolean isSteps) {
        if (isSteps) {
            Type collectionType = new TypeToken<List<Step>>() {
            }.getType();
            return mGson.<List<Step>>fromJson(mList, collectionType);
        } else {
            Type collectionType = new TypeToken<List<Recipe>>() {
            }.getType();
            return mGson.<List<Recipe>>fromJson(mList, collectionType);
        }
    }

}
