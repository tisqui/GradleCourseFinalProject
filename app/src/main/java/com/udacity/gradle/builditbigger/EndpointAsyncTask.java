package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.support.annotation.VisibleForTesting;

import com.example.Joke;
import com.example.squirrel.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

public class EndpointAsyncTask extends AsyncTask<Void, Void, String> {
    public static final String LOG_TAG = EndpointAsyncTask.class.getSimpleName();
    private static MyApi myApiService = null;

    private GetJokeListener mListener = null;
    private Exception mError = null;

    public static interface GetJokeListener {
        public void onComplete(String jokeString, Exception e);
    }

    public EndpointAsyncTask setListener(GetJokeListener listener) {
        this.mListener = listener;
        return this;
    }

    @Override
    protected String doInBackground(Void... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        String joke = new Joke().getJoke();

        try {
            return myApiService.sayJoke(joke).execute().getData();
        } catch (IOException e) {
            mError = e;
            return e.getMessage();
        }
    }

    @Override
    protected void onCancelled() {
        if (this.mListener != null) {
            mError = new InterruptedException("AsyncTask cancelled");
            this.mListener.onComplete(null, mError);
        }
    }

    @VisibleForTesting
    @Override
    protected void onPostExecute(String s) {
        if (this.mListener != null)
            this.mListener.onComplete(s, mError);
    }

}
