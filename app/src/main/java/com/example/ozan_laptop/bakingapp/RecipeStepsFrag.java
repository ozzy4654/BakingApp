package com.example.ozan_laptop.bakingapp;

import android.content.Context;
import android.net.Uri;
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
import com.example.ozan_laptop.bakingapp.data.models.Recipe;
import com.example.ozan_laptop.bakingapp.data.models.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String ARG_POSITION = "arg_position";

    private LinearLayoutManager layoutManager;

    @BindView(R.id.recipe_ingredients_txtview) TextView mIngredients;
    @BindView(R.id.frag_steps_recyclerview) RecyclerView mRecyclerView;

    private RecipeStepsRecyclerAdapter mRecipeAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<Step> mSteps;


    private OnFragmentInteractionListener mListener;

    public RecipeStepsFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeStepsFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeStepsFrag newInstance(Recipe mRecipe) {
        RecipeStepsFrag fragment = new RecipeStepsFrag();
        Bundle args = new Bundle();
        args.putString(RecipeStepsFrag.ARG_PARAM1, convertToJson(mRecipe.getIngredients()));
        args.putString(RecipeStepsFrag.ARG_PARAM2, convertToJson(mRecipe.getSteps()));

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("STEPS", getArguments().getString(ARG_PARAM2));
        savedInstanceState.putString("Ingredients", getArguments().getString(ARG_PARAM1));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null)
            mSteps = (List<Step>) convertToObject( savedInstanceState.getString("STEPS"), true);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            mSteps = (List<Step>) convertToObject( mParam2, true);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        ButterKnife.bind(this, v);

        mIngredients.setText(R.string.recipe_ingredients_title);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecipeAdapter = new RecipeStepsRecyclerAdapter(this);
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
        System.out.println(" WOOOOOOOOOOTOTOTOTOTOTO");
    }

    @OnClick(R.id.recipe_ingredients_txtview)
    public void ingredientsClicked() {
        System.out.println(" WE ARE CLICKING WOOT!!!!!!!");
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
