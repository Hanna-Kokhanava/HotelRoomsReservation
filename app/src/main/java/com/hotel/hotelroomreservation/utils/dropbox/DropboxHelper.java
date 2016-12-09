package com.hotel.hotelroomreservation.utils.dropbox;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.hotel.hotelroomreservation.App;
import com.hotel.hotelroomreservation.constants.Addresses;
import com.hotel.hotelroomreservation.constants.Constants;
import com.hotel.hotelroomreservation.http.HTTPClient;
import com.hotel.hotelroomreservation.model.Reservation;
import com.hotel.hotelroomreservation.model.Room;
import com.hotel.hotelroomreservation.utils.parsers.JSONParser;
import com.hotel.hotelroomreservation.utils.validations.ContextHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DropboxHelper implements IDropboxHelper {
    private Firebase firebase;
    private JSONParser jsonParser;

    public DropboxHelper() {
        jsonParser = new JSONParser();
    }

    public void getRoomList(final DropboxCallback.RoomInfoCallback<Room> listener) {
        String roomsInfo = HTTPClient.getDBInfo(Addresses.ROOMS);
        List<Room> rooms = jsonParser.parseRoomsInfo(roomsInfo);
        listener.onSuccess(rooms);
    }

    public void getReservationListById(final DropboxCallback.ReservationCallback<Date, Calendar> listener, int id) {
        final List<Calendar> reservationDates = new ArrayList<>();
        final List<Date> arrivalDates = new ArrayList<>();
        final SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH);

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

    public void makeReservation(Reservation reservation) {
        Firebase firebase = ((App) ContextHolder.getInstance().getContext()).getFirebaseConnection();
        firebase = firebase.child(Addresses.BOOKINGS).child(String.valueOf(reservation.getId())).push();
        firebase.setValue(reservation);
    }

    public void getBitmapList(final DropboxCallback.RoomInfoCallback<String> listener) {
        firebase = ((App) ContextHolder.getInstance().getContext()).getFirebaseConnection();
        firebase = firebase.child(Addresses.PHOTOS);
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
