package com.hotel.hotelroomreservation;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.firebase.client.Firebase;
import com.hotel.hotelroomreservation.constants.Addresses;
import com.hotel.hotelroomreservation.utils.validations.ContextHolder;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ContextHolder.getInstance().setContext(this);
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }

    public Firebase getFirebaseConnection() {
        return new Firebase(Addresses.FIREBASE_URL);
    }
}
