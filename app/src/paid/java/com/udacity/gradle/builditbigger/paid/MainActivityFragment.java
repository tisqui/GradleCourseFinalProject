package com.udacity.gradle.builditbigger.paid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.squirrel.displayjokeslib.ShowJokeActivity;
import com.udacity.gradle.builditbigger.EndpointAsyncTask;
import com.udacity.gradle.builditbigger.R;

/**
 * Fragment for the paid flavor
 */
public class MainActivityFragment extends Fragment {
    private ProgressBar mSpinner;

    public MainActivityFragment() {
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

        //No ad in the paid version

        Button jokeButton = (Button)root.findViewById(R.id.tell_joke_button);
        if(jokeButton != null){
            jokeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //the beginning of loading - set the spinner to VISIBLE
                    if(mSpinner != null){
                        mSpinner.setVisibility(View.VISIBLE);
                    }

                    new EndpointAsyncTask(){
                        @Override
                        protected void onPostExecute(String result) {
                            //the end of loading - set the spinner visibility to GONE
                            if(mSpinner != null){
                                mSpinner.setVisibility(View.GONE);
                            }
                            //launch the joke intent
                            Intent intent = new Intent(getContext(), ShowJokeActivity.class);
                            intent.putExtra(ShowJokeActivity.JOKE_TAG, result);
                            startActivity(intent);
                        }
                    }.execute();
                }
            });
        }

        Log.d(MainActivityFragment.class.getSimpleName(), "Paid flavor Fragment is created");

        return root;
    }
}
