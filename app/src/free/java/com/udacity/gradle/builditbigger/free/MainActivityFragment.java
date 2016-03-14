package com.udacity.gradle.builditbigger.free;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.squirrel.displayjokeslib.ShowJokeActivity;
import com.udacity.gradle.builditbigger.EndpointAsyncTask;
import com.udacity.gradle.builditbigger.R;


/**
 * Fragment for the free flavor
 */
public class MainActivityFragment extends Fragment {
    private ProgressBar mSpinner;
    private InterstitialAd mInterstitialAd;


    public MainActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        //set the initial spinner visibility to GONE
        mSpinner = (ProgressBar)root.findViewById(R.id.joke_progress_bar);
        if(mSpinner != null){
            mSpinner.setVisibility(View.GONE);
        }

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        // Create the InterstitialAd and set the adUnitId.
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(getString(R.string.interstirial_ad_id));

        //set the action which will be performed when the add is closed - load the joke
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                loadJoke();
            }
        });

        requestNewInterstitial();


        Button jokeButton = (Button)root.findViewById(R.id.tell_joke_button);
        if(jokeButton != null){
            jokeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //if the add is loaded - show the add, if not - load joke activity
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        loadJoke();
                    }
                }
            });
        }

        Log.d(MainActivityFragment.class.getSimpleName(), "Free flavor Fragment is created");
        return root;
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    /**
     * Load the joke activity using EndpointAsynctask
     */
    private void loadJoke(){
        if(mSpinner != null){
            mSpinner.setVisibility(View.VISIBLE);
        }

        new EndpointAsyncTask(){
            @Override
            protected void onPostExecute(String result) {
                if(mSpinner != null){
                    mSpinner.setVisibility(View.GONE);
                }
                Intent intent = new Intent(getContext(), ShowJokeActivity.class);
                intent.putExtra(ShowJokeActivity.JOKE_TAG, result);
                startActivity(intent);

            }
        }.execute();
    }


}
