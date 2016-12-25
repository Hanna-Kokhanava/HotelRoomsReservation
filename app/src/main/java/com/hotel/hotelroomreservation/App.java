package com.hotel.hotelroomreservation;

import android.app.Application;
import android.content.Context;

import com.hotel.hotelroomreservation.database.DatabaseManager;
import com.hotel.hotelroomreservation.database.SQLiteDBHelper;

public class App extends Application {

    private static SQLiteDBHelper dbHelper;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        dbHelper = new SQLiteDBHelper();
        DatabaseManager.initializeInstance(dbHelper);
    }

    public static Context getContext() {
        return context;
    }
}
