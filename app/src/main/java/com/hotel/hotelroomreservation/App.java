package com.hotel.hotelroomreservation;

import android.app.Application;

import com.hotel.hotelroomreservation.utils.ContextHolder;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ContextHolder.setContext(this);
    }

    @Override
    public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.unregisterActivityLifecycleCallbacks(callback);
    }
}
