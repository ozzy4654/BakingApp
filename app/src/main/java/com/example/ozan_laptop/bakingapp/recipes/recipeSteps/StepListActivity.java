package com.example.ozan_laptop.bakingapp.recipes.recipeSteps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.ozan_laptop.bakingapp.R;
import com.example.ozan_laptop.bakingapp.recipes.recipeDetailSteps.StepDetailActivity;
import com.example.ozan_laptop.bakingapp.recipes.recipeIngredients.StepIngredientsActivity;
import com.example.ozan_laptop.bakingapp.data.models.Ingredient;
import com.example.ozan_laptop.bakingapp.data.models.Step;
import com.example.ozan_laptop.bakingapp.recipes.recipeCards.MainActivity;
import com.example.ozan_laptop.bakingapp.recipes.recipeDetailSteps.RecipeStepDetailFrag;
import com.example.ozan_laptop.bakingapp.recipes.recipeIngredients.RecipeIngredientsFrag;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ozan_laptop.bakingapp.utils.NetworkUtils.TYPE_INGREDIENT;
import static com.example.ozan_laptop.bakingapp.utils.NetworkUtils.TYPE_STEPS;
import static com.example.ozan_laptop.bakingapp.utils.NetworkUtils.convertToJson;
import static com.example.ozan_laptop.bakingapp.utils.NetworkUtils.convertToObject;

/**
 * An activity representing a list of Steps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StepListActivity extends AppCompatActivity {

    public static final String INGREDS = "RECIPE_INGREDS";
    public static final String STEPS = "RECIPE_STEPS";
    public static final String INDEX = "STEP_INDEX";
    public static final String STEP_ITEM = "STEP_ITEM";


    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private static List<Ingredient> mIngreds;
    private List<Step> mSteps;

    @BindView(R.id.step_list) RecyclerView mRecyclerView;
    @BindView(R.id.frameLayout) FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState != null) {
            mIngreds =(List<Ingredient>) convertToObject(savedInstanceState.getString(INGREDS), TYPE_INGREDIENT);
            mSteps = (List<Step>) convertToObject(savedInstanceState.getString(STEPS), TYPE_STEPS);
        }else {
            mIngreds = (List<Ingredient>) convertToObject(getIntent().getStringExtra(INGREDS), TYPE_INGREDIENT);
            mSteps = (List<Step>) convertToObject(getIntent().getStringExtra(STEPS), TYPE_STEPS);
        }

        if (mSteps != null) {

            if (!mSteps.get(0).getShortDescription().equalsIgnoreCase(getString(R.string.recipe_ingredients_title))) {
                Step mIngredientStep = new Step();
                mIngredientStep.setShortDescription(getString(R.string.recipe_ingredients_title));
                mSteps.add(0, mIngredientStep);
            }
        }

        if (findViewById(R.id.step_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }


        assert mRecyclerView != null;
        setupRecyclerView(mRecyclerView);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(INGREDS, convertToJson(mIngreds));
        outState.putString(STEPS, convertToJson(mSteps));

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new
                SimpleItemRecyclerViewAdapter(this, mSteps, mTwoPane));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
//            Intent intent = new Intent(this, MainActivity.class);
//            intent.putExtra(STEPS, getIntent().getStringExtra(ARG_STEPS));
//            intent.putExtra(INGREDS, getIntent().getStringExtra(ARG_INGREDIENTS));
            navigateUpTo(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final StepListActivity mParentActivity;
        private final List<Step> mValues;
        private final boolean mTwoPane;
        SimpleItemRecyclerViewAdapter(StepListActivity parent,
                                      List<Step> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_steps_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
                holder.mRecipeStepName.setText(mValues.get(position).getShortDescription());
        }

        @Override
        public int getItemCount() {
            if (mValues == null)
                return 0;
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            @BindView(R.id.recipe_step_name) TextView mRecipeStepName;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    if (mValues.get(getAdapterPosition()).
                            getShortDescription()
                            .equalsIgnoreCase("Recipe Ingredients")) {
                        arguments.putString(INGREDS, convertToJson(mIngreds));
                        RecipeIngredientsFrag fragment = new RecipeIngredientsFrag();
                        fragment.setArguments(arguments);
                        mParentActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.step_detail_container, fragment)
                                .commit();
                    } else {
                        arguments.putString(STEP_ITEM, convertToJson(mValues.get(getAdapterPosition())));
                        arguments.putInt(INDEX, getAdapterPosition());

                        RecipeStepDetailFrag fragment = new RecipeStepDetailFrag();
                        fragment.setArguments(arguments);
                        mParentActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.step_detail_container, fragment)
                                .commit();
                    }

                } else {
                    Context context = view.getContext();
                    Intent intent;
                    if (mValues.get(getAdapterPosition()).
                            getShortDescription()
                            .equalsIgnoreCase("Recipe Ingredients")) {
                        intent = new Intent(context, StepIngredientsActivity.class);
                        intent.putExtra(INGREDS, convertToJson(mIngreds));
                        intent.putExtra(STEP_ITEM, convertToJson(mValues.get(1)));
                        intent.putExtra(STEPS, convertToJson(mValues));

                    } else {
                        intent = new Intent(context, StepDetailActivity.class);

                        intent.putExtra(STEP_ITEM, convertToJson(mValues.get(getAdapterPosition())));
                        intent.putExtra(STEPS, convertToJson(mValues));
                        intent.putExtra(INGREDS, convertToJson(mIngreds));
                        intent.putExtra(INDEX, getAdapterPosition());
                    }
                    context.startActivity(intent);
                }
            }
        }
    }
}
