package com.hotel.hotelroomreservation;

import android.app.Application;

import com.firebase.client.Firebase;
import com.hotel.hotelroomreservation.utils.ContextHolder;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ContextHolder.setContext(this);
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }

    @Override
    public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.unregisterActivityLifecycleCallbacks(callback);
    }
}
