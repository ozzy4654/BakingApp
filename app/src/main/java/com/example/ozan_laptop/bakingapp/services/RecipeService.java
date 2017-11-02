package com.example.ozan_laptop.bakingapp.services;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.JobIntentService;

import com.example.ozan_laptop.bakingapp.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by ozan-laptop on 10/2/2017.
 */

public class RecipeService extends JobIntentService {

    public static final int JOB_ID = 1000;
    public static final String RESULTS = "RESULTS";
    public static final String FAILURE = "FAILURE";
    public static final String ACTION_MyIntentService = "com.example.ozan_laptop.bakingapp.services.RecipeService.RESPONSE";

    /**
     * Convenience method for enqueuing work in to this service.
     */
    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, RecipeService.class, JOB_ID, work);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Request request = new Request.Builder()
                .url(getString(R.string.get_recipes_url))
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
                sendIntentResponse(false, json);
            }
        });
    }

    final Handler mHandler = new Handler();



    private void sendIntentResponse(final boolean hasFailed, @Nullable final String json) {

        mHandler.post(new Runnable() {
            @Override public void run() {
                Intent intentResponse = new Intent();
                intentResponse.setAction(ACTION_MyIntentService);
                intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
                intentResponse.putExtra(RESULTS, json);
                intentResponse.putExtra(FAILURE, hasFailed);

                sendBroadcast(intentResponse);
                stopSelf(JOB_ID);
            }
        });

    }
}
