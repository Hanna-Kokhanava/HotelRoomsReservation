package com.hotel.hotelroomreservation.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hotel.hotelroomreservation.model.Reservation;
import com.hotel.hotelroomreservation.model.Room;

import java.util.ArrayList;
import java.util.List;

//TODO try/final
//is not thread safe

public class SQLiteDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "HotelInfo";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ROOMS = "rooms";
    private static final String TABLE_PHOTOS = "photos";
    private static final String TABLE_BOOKINGS = "bookings";

    private static final String KEY_ROOMS_ID = "id";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_NAME = "name";
    private static final String KEY_RATING = "rating";
    private static final String KEY_VISITORS = "visitors";
    private static final String KEY_ROOMS_URL = "url";
    private static final String KEY_PRICE = "price";

    private static final String KEY_PHOTOS_ID = "id";
    private static final String KEY_PHOTOS_URL = "url";

    private static final String KEY_ID = "room_id";
    private static final String KEY_BOOKINGS_ID = "id";
    private static final String KEY_ARRIVAL = "arrival";
    private static final String KEY_DEPARTURE = "departure";
    private static final String KEY_USER_NAME = "name";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_USER_NUMBER = "number";
    private static final String KEY_EMAIL = "email";

    private static final String CREATE_ROOMS_TABLE = "CREATE TABLE " + TABLE_ROOMS + "("
            + KEY_ROOMS_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT," + KEY_NUMBER + " INTEGER,"
            + KEY_RATING + " INTEGER," + KEY_VISITORS + " INTEGER,"
            + KEY_ROOMS_URL + " TEXT," + KEY_PRICE + " INTEGER" + ")";

    private static final String CREATE_PHOTOS_TABLE = "CREATE TABLE " + TABLE_PHOTOS + "("
            + KEY_PHOTOS_ID + " INTEGER PRIMARY KEY,"
            + KEY_PHOTOS_URL + " TEXT" + ")";

    private static final String CREATE_BOOKINGS_TABLE = "CREATE TABLE " + TABLE_BOOKINGS + "("
            + KEY_BOOKINGS_ID + " INTEGER PRIMARY KEY,"
            + KEY_ID + " TEXT," + KEY_ARRIVAL + " TEXT,"
            + KEY_DEPARTURE + " TEXT," + KEY_USER_NAME + " TEXT,"
            + KEY_SURNAME + " TEXT," + KEY_USER_NUMBER + " TEXT,"
            + KEY_EMAIL + " TEXT" + ")";

    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_ROOMS_TABLE);
        sqLiteDatabase.execSQL(CREATE_PHOTOS_TABLE);
        sqLiteDatabase.execSQL(CREATE_BOOKINGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOMS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);

        onCreate(sqLiteDatabase);
    }

    public void saveRoom(Room room) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, room.getName());
        values.put(KEY_NUMBER, room.getNumber());
        values.put(KEY_RATING, room.getRating());
        values.put(KEY_VISITORS, room.getVisitors());
        values.put(KEY_ROOMS_URL, room.getUrl());
        values.put(KEY_PRICE, room.getPrice());

        db.insert(TABLE_ROOMS, null, values);
        db.close();
    }

    public List<Room> getAllRooms() {
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

    public void saveReservation(Reservation reservation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ID, reservation.getId());
        values.put(KEY_ARRIVAL, reservation.getArrival());
        values.put(KEY_DEPARTURE, reservation.getDeparture());
        values.put(KEY_USER_NAME, reservation.getName());
        values.put(KEY_SURNAME, reservation.getSurname());
        values.put(KEY_USER_NUMBER, reservation.getNumber());
        values.put(KEY_EMAIL, reservation.getEmail());

        db.insert(TABLE_BOOKINGS, null, values);
        db.close();
    }

    public List<Reservation> getAllBookings() {
        List<Reservation> reservations = null;
        String query = "SELECT * FROM " + TABLE_BOOKINGS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            reservations = new ArrayList<>();
            do {
                Reservation reservation = new Reservation();
                reservation.setId(Integer.valueOf(cursor.getString(1)));
                reservation.setArrival(cursor.getString(2));
                reservation.setDeparture(cursor.getString(3));
                reservation.setName(cursor.getString(4));
                reservation.setSurname(cursor.getString(5));
                reservation.setNumber(cursor.getString(6));
                reservation.setEmail(cursor.getString(7));

                reservations.add(reservation);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return reservations;
    }

    public void saveUrl(String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PHOTOS_URL, url);

        db.insert(TABLE_PHOTOS, null, values);
        db.close();
    }

    public List<String> getAllPhotoUrls() {
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

    public boolean isTableExists(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT tbl_name from sqlite_master where tbl_name = '"
                + tableName + "'", null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public void deleteAll(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (isTableExists(tableName)) {
            db.execSQL("DELETE FROM " + tableName);
        } else {
            onCreate(db);
        }

        db.close();
    }
}
