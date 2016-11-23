package com.hotel.hotelroomreservation;

import android.app.Application;
import android.content.Context;

import com.firebase.client.Firebase;
import com.hotel.hotelroomreservation.constants.Addresses;

public class App extends Application {
    public static App app;
    private Firebase firebase;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }

    @Override
    public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.unregisterActivityLifecycleCallbacks(callback);
    }

    public static App getInstance() {
        return app;
    }

    public Firebase getFirebaseConnection() {
        firebase = new Firebase(Addresses.FIREBASE_URL);
        return firebase;
    }
}
