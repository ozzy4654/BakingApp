package com.example.ozan_laptop.bakingapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ozan_laptop.bakingapp.models.Recipe;
import com.example.ozan_laptop.bakingapp.models.RecipeList;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ozan-laptop on 10/2/2017.
 */

public class RecipeCardRecyclerAdapter extends RecyclerView.Adapter<RecipeCardRecyclerAdapter.RecipeCardViewHolder> {

    private final RecipeCardOnClickHandler mClickHandler;
    private RecipeList mRecipesList;


    public interface RecipeCardOnClickHandler {
        void onClick(Recipe mRecipe);
    }

    public RecipeCardRecyclerAdapter( RecipeCardOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @Override
    public RecipeCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_cards, parent, false);
        return new RecipeCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeCardViewHolder holder, int position) {
        holder.mRecipeCardPoster.setText(mRecipesList.recipeResults.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (mRecipesList == null)
            return 0;
        return mRecipesList.recipeResults.size();
    }

    /** this method updates the adapters data
     * @param recipeLists*/
    public void setData(RecipeList recipeLists) {
        if (mRecipesList != null)
            mRecipesList.recipeResults.clear();
        mRecipesList = recipeLists;
        notifyDataSetChanged();
    }

    public class RecipeCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView mRecipeCardPoster;

        public RecipeCardViewHolder(View itemView) {
            super(itemView);
            mRecipeCardPoster = (TextView) itemView.findViewById(R.id.recipe_card_img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mRecipesList != null)
                mClickHandler.onClick(mRecipesList.recipeResults.get(getAdapterPosition()));
        }
    }
}
