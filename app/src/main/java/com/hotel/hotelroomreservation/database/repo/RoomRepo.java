package com.hotel.hotelroomreservation.database.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hotel.hotelroomreservation.database.DatabaseManager;
import com.hotel.hotelroomreservation.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomRepo {

    public static final String TABLE_ROOMS = "rooms";

    private static final String KEY_ROOMS_ID = "id";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_NAME = "name";
    private static final String KEY_RATING = "rating";
    private static final String KEY_VISITORS = "visitors";
    private static final String KEY_ROOMS_URL = "url";
    private static final String KEY_PRICE = "price";

    public static String createTable() {
        return "CREATE TABLE " + TABLE_ROOMS + "("
                + KEY_ROOMS_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT," + KEY_NUMBER + " INTEGER,"
                + KEY_RATING + " INTEGER," + KEY_VISITORS + " INTEGER,"
                + KEY_ROOMS_URL + " TEXT," + KEY_PRICE + " INTEGER" + ")";
    }

    public void insert(final List<Room> rooms) {
        final SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        final ContentValues values = new ContentValues();

        db.beginTransaction();

        try {
            for (final Room room : rooms) {
                values.put(KEY_NAME, room.getName());
                values.put(KEY_NUMBER, room.getNumber());
                values.put(KEY_RATING, room.getRating());
                values.put(KEY_VISITORS, room.getVisitors());
                values.put(KEY_ROOMS_URL, room.getUrl());
                values.put(KEY_PRICE, room.getPrice());

                db.insert(TABLE_ROOMS, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            DatabaseManager.getInstance().closeDatabase();
        }
    }

    public List<Room> getAll() {
        List<Room> rooms = null;

        final SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        final String query = "SELECT * FROM " + TABLE_ROOMS;
        final Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            rooms = new ArrayList<>();
            do {
                final Room room = new Room();
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
        DatabaseManager.getInstance().closeDatabase();

        return rooms;
    }

    public void delete() {
        final SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(TABLE_ROOMS, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }
}
