package com.example.ozan_laptop.bakingapp.fragments.recipeSteps;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ozan_laptop.bakingapp.R;
import com.example.ozan_laptop.bakingapp.fragments.recipeIngredients.RecipeIngredientsFrag;
import com.example.ozan_laptop.bakingapp.data.models.Ingredient;
import com.example.ozan_laptop.bakingapp.data.models.Recipe;
import com.example.ozan_laptop.bakingapp.data.models.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ozan_laptop.bakingapp.utils.NetworkUtils.TYPE_INGREDIENT;
import static com.example.ozan_laptop.bakingapp.utils.NetworkUtils.TYPE_STEPS;
import static com.example.ozan_laptop.bakingapp.utils.NetworkUtils.convertToJson;
import static com.example.ozan_laptop.bakingapp.utils.NetworkUtils.convertToObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeStepsFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeStepsFrag#newInstance} factory method to
 * create an instance of this fragment.
 */


public class RecipeStepsFrag extends Fragment implements RecipeStepsRecyclerAdapter.RecipeStepOnClickHandler {

    private static final String ARG_INGREDIENTS = "INGREDIENTS";
    private static final String ARG_STEPS = "STEPS";

    private List<Step> mSteps;
    private List<Ingredient> mIngredients;
    private OnFragmentInteractionListener mListener;

    @BindView(R.id.frag_steps_recyclerview) RecyclerView mRecyclerView;

    public RecipeStepsFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecipeStepsFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeStepsFrag newInstance(Recipe mRecipe) {
        RecipeStepsFrag fragment = new RecipeStepsFrag();
        Bundle args = new Bundle();
        args.putString(RecipeStepsFrag.ARG_INGREDIENTS, convertToJson(mRecipe.getIngredients()));
        args.putString(RecipeStepsFrag.ARG_STEPS, convertToJson(mRecipe.getSteps()));

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(ARG_STEPS, getArguments().getString(ARG_STEPS));
        savedInstanceState.putString(ARG_INGREDIENTS, getArguments().getString(ARG_INGREDIENTS));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null){
            mSteps = (List<Step>) convertToObject( savedInstanceState.getString(ARG_STEPS),TYPE_STEPS);
            mIngredients = (List<Ingredient>) convertToObject(savedInstanceState.getString(ARG_INGREDIENTS),TYPE_INGREDIENT);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            String ingredients = getArguments().getString(ARG_INGREDIENTS);
            String steps = getArguments().getString(ARG_STEPS);

            mSteps = (List<Step>) convertToObject(steps, TYPE_STEPS);
            mIngredients = (List<Ingredient>) convertToObject(ingredients,TYPE_INGREDIENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        ButterKnife.bind(this, v);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        RecipeStepsRecyclerAdapter mRecipeAdapter = new RecipeStepsRecyclerAdapter(this, getActivity().getApplicationContext());
        mRecyclerView.setAdapter(mRecipeAdapter);

        mRecipeAdapter.setData(mSteps);
        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(Step mStep) {

        // Create fragment and give it an argument for the selected article
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back

        if (mStep.getShortDescription().equalsIgnoreCase(getString(R.string.recipe_ingredients_title))){
            transaction.replace(R.id.fragment_container,  RecipeIngredientsFrag.newInstance(mIngredients));
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
        else
            System.out.println("FUCK THIS ");
//            transaction.replace(R.id.fragment_container,  RecipeStepDetailFrag.newInstance(mStep));
        // TODO: 11/2/2017 fix the rest of the ipmlitation, need to start working on the this fragment.
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
