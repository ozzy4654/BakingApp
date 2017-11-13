package com.example.ozan_laptop.bakingapp.fragments.recipeSteps;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ozan_laptop.bakingapp.R;
import com.example.ozan_laptop.bakingapp.data.models.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;

import com.google.android.exoplayer2.trackselection.TrackSelection;

import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.ozan_laptop.bakingapp.fragments.recipeSteps.RecipeStepsFrag.ARG_INDEX;
import static com.example.ozan_laptop.bakingapp.fragments.recipeSteps.RecipeStepsFrag.ARG_INGREDIENTS;
import static com.example.ozan_laptop.bakingapp.fragments.recipeSteps.RecipeStepsFrag.ARG_STEPS;
import static com.example.ozan_laptop.bakingapp.utils.NetworkUtils.TYPE_STEPS;
import static com.example.ozan_laptop.bakingapp.utils.NetworkUtils.convertToJson;
import static com.example.ozan_laptop.bakingapp.utils.NetworkUtils.convertToObject;

/**
 * Created by Ozan on 11/1/2017.
 */

public class RecipeStepDetailFrag extends Fragment {

    private static final DefaultBandwidthMeter BANDWIDTH_METER =
            new DefaultBandwidthMeter();

    private SimpleExoPlayer mExoPlayer;
    private String mp4String;
    private String thumbnail;
    private long playbackPosition;
    private boolean playWhenReady;
    private int currentWindow;
    private int stepsIndex;

    private List<Step> mSteps;
    private Step mStep;

    @BindView(R.id.playerView) SimpleExoPlayerView mPlayer;
    @BindView(R.id.step_instruction_txt) TextView mStepInstructions;
    @BindView(R.id.prev_step) Button  mPrevStep;
    @BindView(R.id.nxt_step) Button mNextStep;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            String stepDetail = getArguments().getString(ARG_INGREDIENTS);
            String steps = getArguments().getString(ARG_STEPS);

            mSteps = (List<Step>) convertToObject(steps, TYPE_STEPS);
            mStep = convertToObject(stepDetail);
            stepsIndex = getArguments().getInt(ARG_INDEX, 1);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipe_detailed_step, container, false);
        ButterKnife.bind(this, v);

        mStepInstructions.setText(mStep.getDescription());
        mp4String = mStep.getVideoURL();
        thumbnail = mStep.getThumbnailURL();

        setBtnVisibility(stepsIndex);

        setupPlayer();

        return v;
    }

    public static RecipeStepDetailFrag newInstance(Step recipe, List<Step> mSteps, int position) {
        RecipeStepDetailFrag frag = new RecipeStepDetailFrag();
        Bundle args = new Bundle();

        args.putString(ARG_INGREDIENTS, convertToJson(recipe));
        args.putString(ARG_STEPS, convertToJson(mSteps));
        args.putInt(ARG_INDEX, position);

        frag.setArguments(args);
        return frag;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null){
            mSteps = (List<Step>) convertToObject( savedInstanceState.getString(ARG_STEPS),TYPE_STEPS);
            mStep =  convertToObject(savedInstanceState.getString(ARG_INGREDIENTS));
            stepsIndex = savedInstanceState.getInt(ARG_INDEX, 1);
        }
    }

    private void setupPlayer() {

        if (mp4String.equalsIgnoreCase("")){
            mPlayer.setVisibility(View.GONE);
            return;
        }
        if (mExoPlayer == null) {
            mPlayer.setVisibility(View.VISIBLE);
            // a factory to create an AdaptiveVideoTrackSelection
            TrackSelection.Factory adaptiveTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(adaptiveTrackSelectionFactory),
                    new DefaultLoadControl());

            mPlayer.setPlayer(mExoPlayer);
            mExoPlayer.setPlayWhenReady(playWhenReady);
            mExoPlayer.seekTo(currentWindow, playbackPosition);

            Uri uri = Uri.parse(mp4String);
            MediaSource mediaSource = buildMediaSource(uri);
            mExoPlayer.prepare(mediaSource, true, false);

        } else {
            Uri uri = Uri.parse(mp4String);
            MediaSource mediaSource = buildMediaSource(uri);
            mExoPlayer.prepare(mediaSource, true, false);
            mPlayer.setVisibility(View.VISIBLE);

        }

    }

    private void setBtnVisibility(int index) {
        if (index > 1) {
            if (index >= mSteps.size()-1) {
                mNextStep.setVisibility(View.GONE);
            } else
                mNextStep.setVisibility(View.VISIBLE);

            mPrevStep.setVisibility(View.VISIBLE);
        } else {
            mPrevStep.setVisibility(View.GONE);
            mNextStep.setVisibility(View.VISIBLE);

        }
    }

    @OnClick(R.id.nxt_step)
    public void onNextClicked() {

        if (stepsIndex < mSteps.size()-1) {
            stepsIndex = stepsIndex+ 1;
            setBtnVisibility(stepsIndex);
            mStepInstructions.setText( mSteps.get(stepsIndex).getDescription());
            mp4String = mSteps.get(stepsIndex).getVideoURL();
            setupPlayer();
        }
    }

    @OnClick(R.id.prev_step)
    public void onPrevClicked() {
        if (stepsIndex > 1 ) {
            stepsIndex = stepsIndex -1;
            setBtnVisibility(stepsIndex);
            mStepInstructions.setText( mSteps.get(stepsIndex).getDescription());
            mp4String = mSteps.get(stepsIndex).getVideoURL();
            setupPlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        mPlayer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(ARG_STEPS, getArguments().getString(ARG_STEPS));
        savedInstanceState.putString(ARG_INGREDIENTS, getArguments().getString(ARG_INGREDIENTS));
        savedInstanceState.putInt(ARG_INDEX, stepsIndex);

    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(), null, null);
    }


    public RecipeStepDetailFrag() {
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            setupPlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT < 23 || mExoPlayer == null) {
            setupPlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            playbackPosition = mExoPlayer.getCurrentPosition();
            currentWindow = mExoPlayer.getCurrentWindowIndex();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
