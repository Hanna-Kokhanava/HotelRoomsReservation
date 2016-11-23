package com.hotel.hotelroomreservation.utils;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.hotel.hotelroomreservation.App;
import com.hotel.hotelroomreservation.model.Reservation;

public class FirebaseHelper {
    private static Firebase firebase;

    public static void makeReservation(Reservation reservation) {
        firebase = App.getInstance().getFirebaseConnection();
        firebase = firebase.child("bookings").child(String.valueOf(reservation.getId())).push();
        firebase.setValue(reservation);
    }

    public static void getValue() {
        firebase = App.getInstance().getFirebaseConnection();
        firebase = firebase.child("bookings").child(String.valueOf(1));
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot roomSnapshot : dataSnapshot.getChildren()) {
                    Reservation reservation = roomSnapshot.getValue(Reservation.class);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
