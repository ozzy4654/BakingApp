package com.example.ozan_laptop.bakingapp.fragments.recipeCards;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ozan_laptop.bakingapp.R;
import com.example.ozan_laptop.bakingapp.data.models.Recipe;
import com.example.ozan_laptop.bakingapp.data.remote.SOService;
import com.example.ozan_laptop.bakingapp.fragments.recipeSteps.RecipeStepsFrag;
import com.example.ozan_laptop.bakingapp.utils.APIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ozan_laptop.bakingapp.utils.NetworkUtils.TYPE_DEFUALT;
import static com.example.ozan_laptop.bakingapp.utils.NetworkUtils.convertToJson;
import static com.example.ozan_laptop.bakingapp.utils.NetworkUtils.convertToObject;
import static com.example.ozan_laptop.bakingapp.utils.NetworkUtils.isOnline;

/**
 * Created by ozan-laptop on 10/7/2017.
 */

public class RecipeCardFrag extends Fragment implements RecipeCardRecyclerAdapter.RecipeCardOnClickHandler {

    public static final String RECIPE_CARDS = "RECIPE_CARDS";

    private LinearLayoutManager layoutManager;
    private SOService mService;
    private RecipeCardRecyclerAdapter mRecipeAdapter;
    private List<Recipe> mRecipeList;
    private RecipeCardFrag.OnFragmentInteractionListener mListener;

    @BindView(R.id.recipes_list_recycler_view) RecyclerView mRecyclerView;

    public RecipeCardFrag() {
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
    public static RecipeCardFrag newInstance() {
        return new RecipeCardFrag();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipe_cards, container, false);
        ButterKnife.bind(this, v);


        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecipeAdapter = new RecipeCardRecyclerAdapter(this);
        mRecyclerView.setAdapter(mRecipeAdapter);

        if (savedInstanceState == null) {
            mService = APIUtils.getSOService();
            startRecipeService();
            mRecipeAdapter.setData(mRecipeList);
        } else
            mRecipeAdapter.setData((List<Recipe>) convertToObject(savedInstanceState.getString(RECIPE_CARDS), TYPE_DEFUALT));

        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void startRecipeService() {

        if (isOnline(getActivity())) {
            mService.getRecipes().enqueue(new retrofit2.Callback<List<Recipe>>() {
                @Override
                public void onResponse(@NonNull retrofit2.Call<List<Recipe>> call, @NonNull retrofit2.Response<List<Recipe>> response) {
                    mRecipeList = response.body();
                    mRecipeAdapter.setData(mRecipeList);
                }

                @Override
                public void onFailure(retrofit2.Call<List<Recipe>> call, Throwable t) {
                    //do somthing here
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(RECIPE_CARDS, convertToJson(mRecipeList));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState !=  null) {
            mRecipeList = (List<Recipe>) convertToObject(savedInstanceState.getString(RECIPE_CARDS), TYPE_DEFUALT);
            mRecipeAdapter.setData(mRecipeList);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecipeCardFrag.OnFragmentInteractionListener) {
            mListener = (RecipeCardFrag.OnFragmentInteractionListener) context;
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
    public void onClick(Recipe mRecipe) {
        // Create fragment and give it an argument for the selected article
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container,  RecipeStepsFrag.newInstance(mRecipe));
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
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