package com.hotel.hotelroomreservation.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hotel.hotelroomreservation.App;
import com.hotel.hotelroomreservation.database.repo.BookingsRepo;
import com.hotel.hotelroomreservation.database.repo.PhotosRepo;
import com.hotel.hotelroomreservation.database.repo.RoomRepo;

public class SQLiteDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HotelInfo";
    private static final int DATABASE_VERSION = 1;

    public SQLiteDBHelper() {
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(RoomRepo.createTable());
        sqLiteDatabase.execSQL(PhotosRepo.createTable());
        sqLiteDatabase.execSQL(BookingsRepo.createTable());
    }

    @Override
    public void onUpgrade(final SQLiteDatabase sqLiteDatabase, final int i, final int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RoomRepo.TABLE_ROOMS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PhotosRepo.TABLE_PHOTOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BookingsRepo.TABLE_BOOKINGS);
        onCreate(sqLiteDatabase);
    }
}
