package com.example.ozan_laptop.bakingapp.fragments.recipeIngredients;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ozan_laptop.bakingapp.R;
import com.example.ozan_laptop.bakingapp.data.models.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ozan_laptop.bakingapp.utils.NetworkUtils.TYPE_INGREDIENT;
import static com.example.ozan_laptop.bakingapp.utils.NetworkUtils.convertToJson;
import static com.example.ozan_laptop.bakingapp.utils.NetworkUtils.convertToObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeIngredientsFrag extends Fragment {

    private static final String ARG_PARAM1 = "shix";
    private RecipeIngredientsAdapter mRecipeAdapter;
    private List<Ingredient> mIngredients;

    @BindView(R.id.frag_ingredients_recyclerview)
    RecyclerView mIngredientsRV;

    public RecipeIngredientsFrag() {
        // Required empty public constructor
    }

    public static RecipeIngredientsFrag newInstance(List<Ingredient> ingredient) {

        Bundle args = new Bundle();

        RecipeIngredientsFrag fragment = new RecipeIngredientsFrag();
        args.putString(RecipeIngredientsFrag.ARG_PARAM1, convertToJson(ingredient));
        fragment.setArguments(args);
        return fragment;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            mIngredients = (List<Ingredient>) convertToObject( mParam1, TYPE_INGREDIENT);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Ingredients", getArguments().getString(ARG_PARAM1));
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
