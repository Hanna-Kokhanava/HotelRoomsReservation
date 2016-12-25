package com.hotel.hotelroomreservation.database.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hotel.hotelroomreservation.database.DatabaseManager;
import com.hotel.hotelroomreservation.model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class BookingsRepo {

    public static final String TABLE_BOOKINGS = "bookings";

    private static final String KEY_ID = "room_id";
    private static final String KEY_BOOKINGS_ID = "id";
    private static final String KEY_ARRIVAL = "arrival";
    private static final String KEY_DEPARTURE = "departure";
    private static final String KEY_USER_NAME = "name";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_USER_NUMBER = "number";
    private static final String KEY_EMAIL = "email";

    public static String createTable() {
        return "CREATE TABLE " + TABLE_BOOKINGS + "("
                + KEY_BOOKINGS_ID + " INTEGER PRIMARY KEY,"
                + KEY_ID + " TEXT," + KEY_ARRIVAL + " TEXT,"
                + KEY_DEPARTURE + " TEXT," + KEY_USER_NAME + " TEXT,"
                + KEY_SURNAME + " TEXT," + KEY_USER_NUMBER + " TEXT,"
                + KEY_EMAIL + " TEXT" + ")";
    }

    public void insert(final List<Reservation> reservations) {
        final SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        final ContentValues values = new ContentValues();

        db.beginTransaction();

        try {
            for (final Reservation reservation : reservations) {
                values.put(KEY_ID, reservation.getId());
                values.put(KEY_ARRIVAL, reservation.getArrival());
                values.put(KEY_DEPARTURE, reservation.getDeparture());
                values.put(KEY_USER_NAME, reservation.getName());
                values.put(KEY_SURNAME, reservation.getSurname());
                values.put(KEY_USER_NUMBER, reservation.getNumber());
                values.put(KEY_EMAIL, reservation.getEmail());

                db.insert(TABLE_BOOKINGS, null, values);
            }
            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
            DatabaseManager.getInstance().closeDatabase();
        }
    }

    public List<Reservation> selectAll() {
        final SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        List<Reservation> reservations = null;

        final String query = "SELECT * FROM " + TABLE_BOOKINGS;
        final Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            reservations = new ArrayList<>();
            do {
                final Reservation reservation = new Reservation();
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
        DatabaseManager.getInstance().closeDatabase();

        return reservations;
    }

    public void delete() {
        final SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(TABLE_BOOKINGS, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }
}
