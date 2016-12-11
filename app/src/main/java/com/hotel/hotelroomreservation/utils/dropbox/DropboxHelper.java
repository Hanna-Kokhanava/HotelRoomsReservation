package com.hotel.hotelroomreservation.utils.dropbox;

import com.firebase.client.Firebase;
import com.hotel.hotelroomreservation.App;
import com.hotel.hotelroomreservation.constants.Addresses;
import com.hotel.hotelroomreservation.http.HTTPClient;
import com.hotel.hotelroomreservation.model.Reservation;
import com.hotel.hotelroomreservation.model.Room;
import com.hotel.hotelroomreservation.utils.parsers.JSONParser;
import com.hotel.hotelroomreservation.utils.validations.ContextHolder;

import java.util.List;

public class DropboxHelper {
    private JSONParser jsonParser;

    public DropboxHelper() {
        jsonParser = new JSONParser();
    }

    //TODO need empty checking or not?
    public List<Room> getRoomList() {
        String roomsInfo = HTTPClient.getDBInfo(Addresses.ROOMS);
        if (!"".equals(roomsInfo)) {
            return jsonParser.parseRoomsInfo(roomsInfo);
        } else {
            return null;
        }
    }

    public List<Reservation> getReservationListById() {
        String bookingsInfo = HTTPClient.getDBInfo(Addresses.BOOKINGS);
        return jsonParser.parseBookingsInfo(bookingsInfo);
    }

    //TODO change to save in Dropbox
    public void makeReservation(Reservation reservation) {
        Firebase firebase = ((App) ContextHolder.getInstance().getContext()).getFirebaseConnection();
        firebase = firebase.child(Addresses.BOOKINGS).child(String.valueOf(reservation.getId())).push();
        firebase.setValue(reservation);
    }

    public List<String> getUrlsList() {
        String photosUrlsJson = HTTPClient.getDBInfo(Addresses.PHOTOS);
        if (photosUrlsJson != null) {
            return jsonParser.parsePhotoUrls(photosUrlsJson);
        } else {
            return null;
        }
    }
}
