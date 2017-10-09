package com.example.ozan_laptop.bakingapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ozan_laptop.bakingapp.data.models.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.recyclerview.R.attr.layoutManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeIngredientsFrag extends Fragment {

    private RecipeIngredientsAdapter mRecipeAdapter;
    private List<Ingredient> mIngredients;

    @BindView(R.id.frag_ingredients_recyclerview)
    RecyclerView mIngredientsRV;

    public RecipeIngredientsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        ButterKnife.bind(this, v);

//        mIngredients.setText(R.string.recipe_ingredients_title);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mIngredientsRV.setLayoutManager(layoutManager);
        mRecipeAdapter = new RecipeIngredientsAdapter(this);
        mIngredientsRV.setAdapter(mRecipeAdapter);
//
        mRecipeAdapter.setData(mIngredients);
        return v;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setInitialSavedState(SavedState state) {
        super.setInitialSavedState(state);
    }
}
