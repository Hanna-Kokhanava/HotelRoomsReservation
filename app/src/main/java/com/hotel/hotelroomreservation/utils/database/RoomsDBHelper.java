package com.hotel.hotelroomreservation.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hotel.hotelroomreservation.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomsDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "HotelInfo";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ROOMS = "rooms";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_NAME = "name";
    private static final String KEY_RATING = "rating";
    private static final String KEY_VISITORS = "visitors";
    private static final String KEY_URL = "url";
    private static final String KEY_PRICE = "price";

    public RoomsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_ROOMS_TABLE = "CREATE TABLE " + TABLE_ROOMS + "("
                + KEY_NUMBER + " INTEGER," + KEY_NAME + " TEXT,"
                + KEY_RATING + " INTEGER," + KEY_VISITORS + " INTEGER,"
                + KEY_URL + " TEXT," + KEY_PRICE + " INTEGER" + ")";
        sqLiteDatabase.execSQL(CREATE_ROOMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOMS);
    }

    public void save(Room room) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, room.getName());
        values.put(KEY_NUMBER, room.getNumber());
        values.put(KEY_RATING, room.getRating());
        values.put(KEY_VISITORS, room.getVisitors());
        values.put(KEY_URL, room.getUrl());
        values.put(KEY_PRICE, room.getPrice());

        db.insert(TABLE_ROOMS, null, values);
        db.close();
    }

    public List<Room> getAllData() {
        List<Room> rooms = null;
        String query = "SELECT * FROM " + TABLE_ROOMS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            rooms = new ArrayList<>();
            do {
                Room room = new Room();
                room.setName(cursor.getString(1));
                room.setNumber(Integer.valueOf(cursor.getString(2)));
                room.setRating(Integer.valueOf(cursor.getString(3)));
                room.setVisitors(Integer.valueOf(cursor.getString(4)));
                room.setUrl(cursor.getString(5));
                room.setPrice(Integer.valueOf(cursor.getString(6)));

                rooms.add(room);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return rooms;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ROOMS);
        db.close();
    }
}
