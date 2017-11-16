package com.example.ozan_laptop.bakingapp.recipes.recipeIngredients;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ozan_laptop.bakingapp.R;
import com.example.ozan_laptop.bakingapp.data.models.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ozan-laptop on 10/8/2017.
 */

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.RecipeIngredientVH> {

    private List<Ingredient> mIngredients;

    public RecipeIngredientsAdapter(RecipeIngredientsFrag view) {}

    @Override
    public RecipeIngredientVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredient_item, parent, false);
        ButterKnife.bind(view);
        return new RecipeIngredientVH(view);
    }

    @Override
    public void onBindViewHolder(RecipeIngredientVH holder, int position) {
        holder.mIngredientName.setText(mIngredients.get(position).getIngredient());
        holder.mQty.setText(String.valueOf(mIngredients.get(position).getQuantity()));
        holder.mUnits.setText(mIngredients.get(position).getMeasure());
    }

    /** this method updates the adapters data
     * @param recipeIngredients */
    public void setData(List<Ingredient> recipeIngredients) {
        if (mIngredients != null)
            mIngredients.clear();

        mIngredients = recipeIngredients;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mIngredients != null)
            return mIngredients.size();

        return 0;
    }

    public class RecipeIngredientVH extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient_unit) TextView mUnits;
        @BindView(R.id.ingredient_name) TextView mIngredientName;
        @BindView(R.id.ingredient_qty)  TextView mQty;

        public RecipeIngredientVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
