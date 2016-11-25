package com.hotel.hotelroomreservation.utils;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.hotel.hotelroomreservation.App;
import com.hotel.hotelroomreservation.model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FirebaseHelper {
    private static Firebase firebase;
    private static Calendar[] calendarReservationDates;
    private static List<Date> arrivalDates;

    public static void makeReservation(Reservation reservation) {
        firebase = App.getInstance().getFirebaseConnection();
        firebase = firebase.child("bookings").child(String.valueOf(reservation.getId())).push();
        firebase.setValue(reservation);
    }

//    public void getReservationDates(int id) {
//        firebase = App.getInstance().getFirebaseConnection();
//        firebase = firebase.child("bookings").child(String.valueOf(id));
//
//        Log.i("tag", String.valueOf("============="));
//
//        firebase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.i("tag", String.valueOf("+++++++++++"));
//                for (DataSnapshot roomSnapshot : dataSnapshot.getChildren()) {
//                    Reservation reservation = roomSnapshot.getValue(Reservation.class);
//                }
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
//    }
}
