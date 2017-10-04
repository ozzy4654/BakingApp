package com.example.ozan_laptop.bakingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ozan_laptop.bakingapp.models.Recipe;
import com.example.ozan_laptop.bakingapp.models.RecipeList;
import com.example.ozan_laptop.bakingapp.services.RecipeService;
import com.google.gson.Gson;


import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ozan_laptop.bakingapp.utils.NetworkUtils.isOnline;

public class MainActivity extends AppCompatActivity implements RecipeCardRecyclerAdapter.RecipeCardOnClickHandler {

    private RecipeListBroadcastReceiver recipeListBroadcastReceiver;

    private RecipeCardRecyclerAdapter mRecipeAdapter;
    private LinearLayoutManager layoutManager;
    private boolean isReg = false;

    @BindView(R.id.recipes_list_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.error_msg)
    TextView mErrorMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecipeAdapter = new RecipeCardRecyclerAdapter(this);
        mRecyclerView.setAdapter(mRecipeAdapter);

        recipeListBroadcastReceiver = new RecipeListBroadcastReceiver();

        mProgressBar.setVisibility(View.INVISIBLE);
        mErrorMsg.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.INVISIBLE);

        IntentFilter intentFilter = new IntentFilter(RecipeService.ACTION_MyIntentService);
        intentFilter.addAction(Intent.CATEGORY_DEFAULT);
        registerReceiver(recipeListBroadcastReceiver, intentFilter);
        isReg = true;

        startRecipeService();
    }


    private void startRecipeService() {

        if (isOnline(this)) {
            Intent mServiceIntent = new Intent(this, RecipeService.class);
            RecipeService.enqueueWork(this, mServiceIntent);

        } else
            showError();
    }



    @Override
    public void onClick(Recipe mRecipe) {
        //no op right now
    }

    @Override
    protected void onPause() {
        if (isReg) {
            isReg = false;
            unregisterReceiver(recipeListBroadcastReceiver);
        }
        super.onPause();
    }


    private void showError() {
        mRecyclerView.setVisibility(View.GONE);
        mErrorMsg.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    private void setAdapter(String stringExtra) {

        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        Gson mGson = new Gson();
        RecipeList mRecipeList = mGson.fromJson(stringExtra, RecipeList.class);

        mRecipeAdapter.setData(mRecipeList);

    }


    private class RecipeListBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            setAdapter(intent.getStringExtra(RecipeService.RESULTS));
        }
    }
}
