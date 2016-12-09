package com.hotel.hotelroomreservation.utils.dropbox;

import com.hotel.hotelroomreservation.model.Reservation;
import com.hotel.hotelroomreservation.model.Room;

import java.util.Calendar;
import java.util.Date;

public interface IDropboxHelper {
    void makeReservation(Reservation reservation);
    void getRoomList(DropboxCallback.RoomInfoCallback<Room> listener);
    void getBitmapList(DropboxCallback.RoomInfoCallback<String> listener);
    void getReservationListById(DropboxCallback.ReservationCallback<Date, Calendar> listener, int id);

}
