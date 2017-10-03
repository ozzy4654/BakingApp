package com.example.ozan_laptop.bakingapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ozan-laptop on 10/2/2017.
 */

public class RecipeList {

    @SerializedName("results")
    @Expose
    public List<Recipe> recipeResults = null;

}
