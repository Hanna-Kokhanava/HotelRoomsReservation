package com.hotel.hotelroomreservation.utils;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.hotel.hotelroomreservation.App;
import com.hotel.hotelroomreservation.model.Reservation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FirebaseHelper {
    private static Firebase firebase;
    private static List<Calendar> reservationDates = new ArrayList<>();

    public static void makeReservation(Reservation reservation) {
        firebase = App.getInstance().getFirebaseConnection();
        firebase = firebase.child("bookings").child(String.valueOf(reservation.getId())).push();
        firebase.setValue(reservation);
    }

    public static void getReservationsList(int id) {
        final Calendar arrivalCalendar = Calendar.getInstance();

        firebase = App.getInstance().getFirebaseConnection();
        firebase = firebase.child("bookings").child(String.valueOf(id));

        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot roomSnapshot : dataSnapshot.getChildren()) {
                    Reservation reservation = roomSnapshot.getValue(Reservation.class);

                    long date = Long.parseLong(reservation.getArrival());
                    arrivalCalendar.setTimeInMillis(date);
                    reservationDates.add(arrivalCalendar);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


}
