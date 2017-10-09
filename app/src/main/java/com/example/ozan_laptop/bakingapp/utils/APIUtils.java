package com.example.ozan_laptop.bakingapp.utils;

import com.example.ozan_laptop.bakingapp.data.remote.RetrofitClient;
import com.example.ozan_laptop.bakingapp.data.remote.SOService;

/**
 * Created by ozan-laptop on 10/4/2017.
 */

public class APIUtils {

    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}
