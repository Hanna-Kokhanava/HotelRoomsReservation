package com.hotel.hotelroomreservation.database.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hotel.hotelroomreservation.database.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class PhotosRepo {

    public static final String TABLE_PHOTOS = "photos";

    private static final String KEY_PHOTOS_ID = "id";
    private static final String KEY_PHOTOS_URL = "url";

    public static String createTable() {
        return "CREATE TABLE " + TABLE_PHOTOS + "("
                + KEY_PHOTOS_ID + " INTEGER PRIMARY KEY,"
                + KEY_PHOTOS_URL + " TEXT" + ")";
    }

    public void insert(final List<String> urls) {
        final SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        final ContentValues values = new ContentValues();

        db.beginTransaction();

        try {
            for (final String url : urls) {
                values.put(KEY_PHOTOS_URL, url);
                db.insert(TABLE_PHOTOS, null, values);
            }
            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
            DatabaseManager.getInstance().closeDatabase();
        }
    }

    public List<String> selectAll() {
        List<String> urls = null;

        final SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        final String query = "SELECT * FROM " + TABLE_PHOTOS;
        final Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            urls = new ArrayList<>();
            do {
                urls.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return urls;
    }

    public void delete() {
        final SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(TABLE_PHOTOS, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }
}
