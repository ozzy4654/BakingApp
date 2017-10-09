package com.example.ozan_laptop.bakingapp;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ozan_laptop.bakingapp.data.models.Recipe;
import com.example.ozan_laptop.bakingapp.data.remote.SOService;
import com.example.ozan_laptop.bakingapp.utils.APIUtils;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ozan_laptop.bakingapp.utils.NetworkUtils.convertToJson;
import static com.example.ozan_laptop.bakingapp.utils.NetworkUtils.isOnline;

public class MainActivity extends AppCompatActivity implements RecipeStepsFrag.OnFragmentInteractionListener, RecipeCardFrag.OnFragmentInteractionListener {

    @BindView(R.id.fragment_container)
    FrameLayout mFragContainer;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.error_msg)
    TextView mErrorMsg;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("DONT", "Happy");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mProgressBar.setVisibility(View.INVISIBLE);
        mErrorMsg.setVisibility(View.GONE);
        mFragContainer.setVisibility(View.VISIBLE);


        if (savedInstanceState == null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.add(R.id.fragment_container,  RecipeCardFrag.newInstance());
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


//    private void showError() {
//        mFragContainer.setVisibility(View.GONE);
//        mErrorMsg.setVisibility(View.VISIBLE);
//        mProgressBar.setVisibility(View.GONE);
//    }
//

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
