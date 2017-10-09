package com.example.ozan_laptop.bakingapp.data.remote;

import com.example.ozan_laptop.bakingapp.data.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ozan-laptop on 10/4/2017.
 */
public interface SOService {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();

//    @GET("")
//    Call<> getAnswers(@Query("tagged") String tags);
}