package com.hotel.hotelroomreservation;

import android.app.Application;

import com.firebase.client.Firebase;

import net.danlew.android.joda.JodaTimeAndroid;

public class App extends Application {

    private Firebase mFirebase;

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        JodaTimeAndroid.init(this);
        mFirebase = new
    }

    @Override
    public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.unregisterActivityLifecycleCallbacks(callback);
    }

    @Override
    public Object getSystemService(String name) {
        if(name.equals("you_key")) {
            return mFirebase;
        }
        return super.getSystemService(name);
    }
}
