package com.example.ozan_laptop.bakingapp.recipes.recipeIngredients;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ozan_laptop.bakingapp.R;
import com.example.ozan_laptop.bakingapp.recipes.recipeDetailSteps.StepDetailActivity;
import com.example.ozan_laptop.bakingapp.data.models.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

import static com.example.ozan_laptop.bakingapp.recipes.recipeSteps.StepListActivity.INGREDS;
import static com.example.ozan_laptop.bakingapp.recipes.recipeSteps.StepListActivity.STEPS;
import static com.example.ozan_laptop.bakingapp.recipes.recipeSteps.StepListActivity.STEP_ITEM;
import static com.example.ozan_laptop.bakingapp.utils.NetworkUtils.TYPE_INGREDIENT;
import static com.example.ozan_laptop.bakingapp.utils.NetworkUtils.convertToObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeIngredientsFrag extends Fragment {

    private RecipeIngredientsAdapter mRecipeAdapter;
    private List<Ingredient> mIngredients;

    @BindView(R.id.frag_ingredients_recyclerview)
    RecyclerView mIngredientsRV;

    @Nullable
    @BindView(R.id.nxt_step_btn) Button mNextStep;

    public RecipeIngredientsFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
        ButterKnife.bind(this, v);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mIngredientsRV.setLayoutManager(layoutManager);
        mRecipeAdapter = new RecipeIngredientsAdapter(this);
        mIngredientsRV.setAdapter(mRecipeAdapter);
        mRecipeAdapter.setData(mIngredients);

        return v;

    }

    @Optional
    @OnClick(R.id.nxt_step_btn)
    public void onClick () {

        Intent intent = new Intent(getActivity(), StepDetailActivity.class);
        intent.putExtra(STEP_ITEM, getActivity().getIntent().getStringExtra(STEP_ITEM));
        intent.putExtra(STEPS, getActivity().getIntent().getStringExtra(STEPS));
        intent.putExtra(INGREDS, getActivity().getIntent().getStringExtra(INGREDS));
        startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String ingredients = getArguments().getString(INGREDS);
            mIngredients = (List<Ingredient>) convertToObject( ingredients, TYPE_INGREDIENT);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Ingredients", getArguments().getString(INGREDS));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null)
            mIngredients = (List<Ingredient>) convertToObject( savedInstanceState.getString("Ingredients"),TYPE_INGREDIENT);

    }

    @Override
    public void setInitialSavedState(SavedState state) {
        super.setInitialSavedState(state);
    }
}
