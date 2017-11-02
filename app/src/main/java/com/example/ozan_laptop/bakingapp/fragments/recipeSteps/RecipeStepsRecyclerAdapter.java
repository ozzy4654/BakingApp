package com.example.ozan_laptop.bakingapp.fragments.recipeSteps;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ozan_laptop.bakingapp.R;
import com.example.ozan_laptop.bakingapp.data.models.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by ozan-laptop on 10/7/2017.
 */

public class RecipeStepsRecyclerAdapter extends RecyclerView.Adapter<RecipeStepsRecyclerAdapter.RecipeStepsViewHolder> {

    private final RecipeStepOnClickHandler mStepClickHandler;
    private List<Step> mRecipeSteps;
    private Context mContext;

    public interface RecipeStepOnClickHandler {
        void onClick(Step mStep);
    }

    public RecipeStepsRecyclerAdapter( RecipeStepOnClickHandler stepClickHandler, Context context) {
        mStepClickHandler = stepClickHandler;
        mContext = context;
    }

    @Override
    public RecipeStepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_steps_item, parent, false);
        return new RecipeStepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeStepsViewHolder holder, int position) {
        holder.mRecipeStepName.setText(mRecipeSteps.get(position).getShortDescription());

    }

    @Override
    public int getItemCount() {
        if (mRecipeSteps == null)
            return 0;
        return mRecipeSteps.size();
    }

    /** this method updates the adapters data
     * @param recipeSteps */
    public void setData(List<Step> recipeSteps) {
        if (mRecipeSteps != null)
            mRecipeSteps.clear();

        mRecipeSteps = recipeSteps;

        if (!mRecipeSteps.get(0).getShortDescription().equalsIgnoreCase(mContext.getString(R.string.recipe_ingredients_title))) {
            Step mIngredientStep = new Step();
            mIngredientStep.setShortDescription(mContext.getString(R.string.recipe_ingredients_title));
            mRecipeSteps.add(0, mIngredientStep);
        }
        notifyDataSetChanged();
    }

    public class RecipeStepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipe_step_name) TextView mRecipeStepName;

        public RecipeStepsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mRecipeSteps != null) {
                mStepClickHandler.onClick(mRecipeSteps.get(getAdapterPosition()));
            }
        }
    }
}
