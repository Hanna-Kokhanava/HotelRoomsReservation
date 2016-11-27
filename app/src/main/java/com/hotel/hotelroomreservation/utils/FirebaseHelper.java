package com.hotel.hotelroomreservation.utils;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.hotel.hotelroomreservation.App;
import com.hotel.hotelroomreservation.constants.Addresses;
import com.hotel.hotelroomreservation.model.Reservation;
import com.hotel.hotelroomreservation.utils.validations.ContextHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FirebaseHelper {
    private static Firebase firebase;
    private static List<Date> arrivalDates;
    private static Calendar[] calendarDates;

    public static void makeReservation(Reservation reservation) {
        firebase = ((App) ContextHolder.getInstance().getContext()).getFirebaseConnection();
        firebase = firebase.child(Addresses.BOOKINGS).child(String.valueOf(reservation.getId())).push();
        firebase.setValue(reservation);
    }

    public static void getRoomReservationDates(int id) {
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

                calendarDates = new Calendar[reservationDates.size()];
                calendarDates = reservationDates.toArray(calendarDates);

                setCalendarDates(calendarDates);
                setArrivalDates(arrivalDates);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public static List<Date> getArrivalDates() {
        return arrivalDates;
    }

    public static void setArrivalDates(List<Date> arrivalDates) {
        FirebaseHelper.arrivalDates = arrivalDates;
    }

    public static Calendar[] getCalendarDates() {
        return calendarDates;
    }

    public static void setCalendarDates(Calendar[] calendarDates) {
        FirebaseHelper.calendarDates = calendarDates;
    }
}
