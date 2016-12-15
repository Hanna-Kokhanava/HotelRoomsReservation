package com.hotel.hotelroomreservation.utils.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookingsDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "HotelInfo";
    private static final String TABLE_ROOMS = "rooms";
    private static final String TABLE_BOOKINGS = "bookings";
    private static final String TABLE_PHOTOS = "photos";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_COUNTRY = "country";

    public BookingsDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
