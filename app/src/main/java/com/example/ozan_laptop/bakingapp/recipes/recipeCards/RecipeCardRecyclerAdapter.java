package com.example.ozan_laptop.bakingapp.recipes.recipeCards;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ozan_laptop.bakingapp.R;
import com.example.ozan_laptop.bakingapp.data.models.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ozan-laptop on 10/2/2017.
 */

public class RecipeCardRecyclerAdapter extends RecyclerView.Adapter<RecipeCardRecyclerAdapter.RecipeCardViewHolder> {

    private final RecipeCardOnClickHandler mClickHandler;
    private List<Recipe> mRecipesList;


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
        holder.mRecipeCardPoster.setText(mRecipesList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (mRecipesList == null)
            return 0;
        return mRecipesList.size();
    }

    /** this method updates the adapters data
     * @param recipeLists*/
    public void setData(List<Recipe> recipeLists) {
        if (mRecipesList != null)
            mRecipesList.clear();
        mRecipesList = recipeLists;
        notifyDataSetChanged();
    }

    public class RecipeCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipe_card_img) TextView mRecipeCardPoster;

        public RecipeCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mRecipesList != null)
                mClickHandler.onClick(mRecipesList.get(getAdapterPosition()));
        }
    }
}
