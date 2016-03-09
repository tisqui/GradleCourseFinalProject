package com.squirrel.displayjokeslib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ShowJokeActivity extends AppCompatActivity {
    public static final String JOKE_TAG = "joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_joke);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String joke = intent.getStringExtra(JOKE_TAG);
            TextView jokeTextView = (TextView) findViewById(R.id.joke_text);
            if(jokeTextView != null){
                if(joke != null) {
                    jokeTextView.setText(joke);
                } else {
                    jokeTextView.setText(R.string.no_joke_text);
                }
            }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
