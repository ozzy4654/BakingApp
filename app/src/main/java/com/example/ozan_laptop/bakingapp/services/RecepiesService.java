package com.example.ozan_laptop.bakingapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.ozan_laptop.bakingapp.models.Recipe;
import com.example.ozan_laptop.bakingapp.models.RecipeList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ozan-laptop on 10/2/2017.
 */

public class RecepiesService extends IntentService{

    public static final String RESULTS = "RESULTS";
    private static final String FAILURE = "FAILURE";
    private static final String ACTION_MyIntentService = "com.example.ozan_laptop.bakingapp.services.RecipiesServices";
    private String mBaseUrl;


    public  RecepiesService(){
        super("com.example.ozan_laptop.bakingapp.services.RecipiesServices");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Request request = new Request.Builder()
                .url(mBaseUrl)
                .build();

        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                sendIntentResponse(true, null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                sendIntentResponse(true, json);
            }
        });


    }

    private void sendIntentResponse(boolean hasFailed, @Nullable String json) {
        Intent intentResponse = new Intent();
        intentResponse.setAction(ACTION_MyIntentService);
        intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
        intentResponse.putExtra(RESULTS, json);

        intentResponse.putExtra(FAILURE, hasFailed);

        sendBroadcast(intentResponse);
        stopSelf();

    }
}
