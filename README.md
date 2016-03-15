# Udacity nanodegree project P4 - Build It Bigger
Udacity gradle course project for my nanodegree android development course. App retrieves jokes from Google Cloud Endpoints module and displays them via an Activity from the Android Library.

###Features
 -  Project contains a Java library for supplying jokes
 - Project contains an Android library with an activity that displays jokes passed to it as intent extras.
 - Project contains a Google Cloud Endpoints module that supplies jokes from the Java library. Project loads jokes from GCE module via an async task.
 - Project contains connected tests to verify that the async task is indeed loading jokes.
 - Project contains paid/free flavors. The paid flavor has no ads, and no unnecessary dependencies.
 - Free app variant displays interstitial ads between the main activity and the joke-displaying activity.
 - App display a loading indicator while the joke is being fetched from the server.
 - Contains Gradle task that starts the GCE dev server, runs all the Android tests, and shuts down the dev server.
 


