package com.example.ozan_laptop.bakingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ozan_laptop.bakingapp.models.RecipeList;
import com.example.ozan_laptop.bakingapp.services.RecepiesService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private RecipeListBroadcastReceiver recipeListBroadcastReceiver;

    private RecipeCardRecyclerAdapter mRecipeAdapter;
    private LinearLayoutManager layoutManager;

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

    }










    private class RecipeListBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!intent.getBooleanExtra("hasFaild", false)) {
                setAdapter(intent.getStringExtra(RecepiesService.RESULTS));
            } else
                showError();
        }
    }

    private void showError() {
    }

    private void setAdapter(String stringExtra) {
        Gson mGson = new Gson();
        mGson.fromJson(stringExtra, RecipeList.class);

    }
}
}
