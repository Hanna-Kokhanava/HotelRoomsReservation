package com.hotel.hotelroomreservation.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.hotel.hotelroomreservation.model.Room;

import java.util.ArrayList;
import java.util.List;

public class PhotosDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "HotelInfo";

    private static final String TABLE_PHOTOS = "photos";
    private static final String KEY_URL = "url";

    public PhotosDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_ROOMS_TABLE = "CREATE TABLE " + TABLE_PHOTOS + "("
                + KEY_URL + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_ROOMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTOS);
    }

    public void save(String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_URL, url);

        db.insert(TABLE_PHOTOS, null, values);
        db.close();
    }

    public List<String> getAllData() {
        List<String> urls = null;
        String query = "SELECT * FROM " + TABLE_PHOTOS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            urls = new ArrayList<>();
            do {
                urls.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return urls;
    }

    public boolean isTableExists() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT tbl_name from sqlite_master where tbl_name = '"
                + TABLE_PHOTOS + "'", null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();

        if (isTableExists()) {
            db.execSQL("DELETE FROM " + TABLE_PHOTOS);
        }

        db.close();
    }
}
