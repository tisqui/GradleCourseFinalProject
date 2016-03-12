package com.udacity.gradle.builditbigger.paid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.squirrel.displayjokeslib.ShowJokeActivity;
import com.udacity.gradle.builditbigger.EndpointAsyncTask;
import com.udacity.gradle.builditbigger.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        //No ad in the paid version

        Button jokeButton = (Button)root.findViewById(R.id.tell_joke_button);
        if(jokeButton != null){
            jokeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new EndpointAsyncTask(){
                        @Override
                        protected void onPostExecute(String result) {
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
