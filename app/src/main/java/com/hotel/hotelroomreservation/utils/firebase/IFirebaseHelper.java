package com.hotel.hotelroomreservation.utils.firebase;

import com.hotel.hotelroomreservation.model.Reservation;
import com.hotel.hotelroomreservation.model.Room;

import java.util.Calendar;
import java.util.Date;

public interface IFirebaseHelper {
    void makeReservation(Reservation reservation);
    void getRoomList(FirebaseCallback.RoomInfoCallback<Room> listener);
    void getBitmapList(FirebaseCallback.RoomInfoCallback<String> listener);
    void getReservationListById(FirebaseCallback.ReservationCallback<Date, Calendar> listener, int id);

}
