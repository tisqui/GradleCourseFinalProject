package com.udacity.gradle.builditbigger;
import android.app.Application;
import android.test.ApplicationTestCase;
import android.text.TextUtils;

import java.util.concurrent.CountDownLatch;

/**
 * Created by squirrel on 3/11/16.
 */
public class AsyncTaskTest extends ApplicationTestCase<Application> {
    public final String TAG = AsyncTaskTest.class.getSimpleName();
    public final String JOKE_TEXT = "This is a joke! Very funny one...";

    String mJokeString = null;
    Exception mError = null;
    CountDownLatch signal = null;

    public AsyncTaskTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        signal.countDown();
    }

    public void testJokeGetTask() throws InterruptedException {


        new EndpointAsyncTask().setListener(new EndpointAsyncTask.GetJokeListener() {
            @Override
            public void onComplete(String jokeString, Exception e) {
                mJokeString = jokeString;
                mError = e;
                signal.countDown();
            }
        }).execute();
        signal.await();

        assertNull(mError);
        assertFalse(TextUtils.isEmpty(mJokeString));
        assertTrue(mJokeString.equals(JOKE_TEXT));
    }


}
