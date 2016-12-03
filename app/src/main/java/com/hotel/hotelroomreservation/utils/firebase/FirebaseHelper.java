package com.hotel.hotelroomreservation.utils.firebase;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.hotel.hotelroomreservation.App;
import com.hotel.hotelroomreservation.constants.Addresses;
import com.hotel.hotelroomreservation.constants.Constants;
import com.hotel.hotelroomreservation.model.Reservation;
import com.hotel.hotelroomreservation.model.Room;
import com.hotel.hotelroomreservation.utils.validations.ContextHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FirebaseHelper {
    private Firebase firebase;

    public void getRoomList(final FirebaseCallback.RoomInfoCallback<Room> listener) {
        firebase = ((App) ContextHolder.getInstance().getContext()).getFirebaseConnection();
        firebase.keepSynced(true);
        firebase.child(Constants.ROOMS_KEY).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<Room> rooms = new ArrayList<>();

                for (DataSnapshot roomSnapshot : snapshot.getChildren()) {
                    Room room = roomSnapshot.getValue(Room.class);
                    rooms.add(room);
                }

                listener.onSuccess(rooms);
            }

            @Override
            public void onCancelled(FirebaseError error) {

            }
        });
    }

    public void getReservationList(final FirebaseCallback.ReservationCallback<Date, Calendar> listener, int id) {
        final List<Calendar> reservationDates = new ArrayList<>();
        final List<Date> arrivalDates = new ArrayList<>();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        firebase = ((App) ContextHolder.getInstance().getContext()).getFirebaseConnection();
        firebase = firebase.child(Addresses.BOOKINGS).child(String.valueOf(id));
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Date dateArrival = null;
                Date dateDeparture = null;

                for (DataSnapshot roomSnapshot : dataSnapshot.getChildren()) {
                    Reservation reservation = roomSnapshot.getValue(Reservation.class);

                    try {
                        dateArrival = sdf.parse(reservation.getArrival());
                        dateDeparture = sdf.parse(reservation.getDeparture());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    arrivalDates.add(dateArrival);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dateArrival);

                    while (calendar.getTime().before(dateDeparture)) {
                        Calendar c = Calendar.getInstance();
                        c.setTime(calendar.getTime());
                        reservationDates.add(c);
                        calendar.add(Calendar.DATE, 1);
                    }
                }

                listener.onSuccess(arrivalDates, reservationDates);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public static void makeReservation(Reservation reservation) {
        Firebase firebase = ((App) ContextHolder.getInstance().getContext()).getFirebaseConnection();
        firebase = firebase.child(Addresses.BOOKINGS).child(String.valueOf(reservation.getId())).push();
        firebase.setValue(reservation);
    }

    public void getBitmapList(final FirebaseCallback.RoomInfoCallback<String> listener) {
        firebase = ((App) ContextHolder.getInstance().getContext()).getFirebaseConnection();
        firebase = firebase.child(Constants.PHOTOS_KEY);
        firebase.addValueEventListener(new ValueEventListener() {
            List<String> hotelPhotosUrls = new ArrayList<>();

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot urlSnapshot : snapshot.getChildren()) {
                    hotelPhotosUrls.add((String) urlSnapshot.getValue());
                }

                listener.onSuccess(hotelPhotosUrls);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
