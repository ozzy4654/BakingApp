package com.example.ozan_laptop.bakingapp.recipes.recipeCards;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ozan_laptop.bakingapp.R;
import com.example.ozan_laptop.bakingapp.recipes.recipeCards.RecipeCardFrag;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeCardFrag.OnFragmentInteractionListener {

    @BindView(R.id.fragment_container)
    FrameLayout mFragContainer;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.error_msg)
    TextView mErrorMsg;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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


    private void showError() {
        mFragContainer.setVisibility(View.GONE);
        mErrorMsg.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
















//    // take some collection of numbers and return the min value
//
//
//    private Integer findMind(ArrayList<Integer> numList) {
//
//        Integer temp = null;
//
//
//        if (numList.size() > 0 )
//            temp = numList.get(0);
//        else
//            return null;
//
//        for (int i = 1; i < numList.size(); i++) {
//            if (temp > numList.get(i))
//                temp = numList.get(i);
//        }
//        return temp;
//    }
//
//    public class MinStack {
//
//        private Stack<Integer> numStack = new Stack<>();
//
//        public int pop() {
//            return numStack.pop();
//        }
//
//        public void push(int value) {
//            numStack.push(value);
//        }
//
//
//        public int getMin() {
//
//            Integer temp = null;
//
//            for (Integer numStackValue : numStack) {
//                if (temp == null || temp > numStackValue)
//                    temp = numStackValue;
//            }
//             return temp;
//        }
//    }




}
