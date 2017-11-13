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

    private static final Gson mGson = new Gson();
    public static final int TYPE_STEPS = 0;
    public static final int TYPE_INGREDIENT = 1;
    public static final int TYPE_DEFUALT = -1;


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


    public static String convertToJson(Object mList) {
        return mGson.toJson(mList);
    }


    /**
     * This method allow the app to convert our arrays of objects
     * to objects of the desired type.
     */
    public static List<?> convertToObject(String mList, int caseNum) {
        Type collectionType;

        switch (caseNum) {
            case TYPE_STEPS:
                collectionType = new TypeToken<List<Step>>() {}.getType();
                return mGson.<List<Step>>fromJson(mList, collectionType);
            case TYPE_INGREDIENT:
                collectionType = new TypeToken<List<Ingredient>>() {}.getType();
                return mGson.<List<Ingredient>>fromJson(mList, collectionType);
            default:
                collectionType = new TypeToken<List<Recipe>>() {}.getType();
                return mGson.<List<Recipe>>fromJson(mList, collectionType);
        }
    }

    public static Step convertToObject(String step) {
        return mGson.fromJson(step, Step.class);
    }
}
