package com.hotel.hotelroomreservation;

import android.app.Application;

import com.hotel.hotelroomreservation.utils.validations.ContextHolder;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ContextHolder.getInstance().setContext(this);
    }
}
