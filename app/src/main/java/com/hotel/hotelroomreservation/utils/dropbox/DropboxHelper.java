package com.hotel.hotelroomreservation.utils.dropbox;

import com.hotel.hotelroomreservation.constants.Addresses;
import com.hotel.hotelroomreservation.http.HTTPClient;
import com.hotel.hotelroomreservation.model.Reservation;
import com.hotel.hotelroomreservation.model.Room;
import com.hotel.hotelroomreservation.utils.parsers.JSONParser;

import java.util.List;

public class DropboxHelper {
    private final JSONParser jsonParser;
    private String bookingsInfo;

    public String getBookingsInfo() {
        return bookingsInfo;
    }

    public DropboxHelper() {
        jsonParser = new JSONParser();
    }

    public List<Room> getRoomList() {
        final String roomsInfo = HTTPClient.getDBInfo(Addresses.ROOMS);
        if (!"".equals(roomsInfo)) {
            return jsonParser.parseRoomsInfo(roomsInfo);
        } else {
            return null;
        }
    }

    public List<Reservation> getReservationListById() {
        bookingsInfo = HTTPClient.getDBInfo(Addresses.BOOKINGS);
        return jsonParser.parseBookingsInfo(bookingsInfo);
    }

    public void makeReservation(final String reservation) {
        HTTPClient.setDBBookingsInfo(reservation);
    }

    public List<String> getUrlsList() {
        final String photosUrlsJson = HTTPClient.getDBInfo(Addresses.PHOTOS);
        if (photosUrlsJson != null) {
            return jsonParser.parsePhotoUrls(photosUrlsJson);
        } else {
            return null;
        }
    }
}
