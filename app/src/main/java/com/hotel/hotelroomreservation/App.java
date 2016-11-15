package com.hotel.hotelroomreservation;

import android.app.Application;

import com.firebase.client.Firebase;

import net.danlew.android.joda.JodaTimeAndroid;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        JodaTimeAndroid.init(this);
    }

    @Override
    public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.unregisterActivityLifecycleCallbacks(callback);
    }
}
