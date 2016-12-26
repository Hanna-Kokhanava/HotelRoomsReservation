package com.hotel.hotelroomreservation.utils.dropbox;

import com.hotel.hotelroomreservation.constants.Addresses;
import com.hotel.hotelroomreservation.http.HTTPClient;
import com.hotel.hotelroomreservation.model.Reservation;
import com.hotel.hotelroomreservation.model.Room;
import com.hotel.hotelroomreservation.utils.parsers.JSONParser;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class DropboxHelper {
    private final JSONParser jsonParser;
    private final HTTPClient mHTTPClient;
    private String bookingsInfo;

    public String getBookingsInfo() {
        return bookingsInfo;
    }

    public DropboxHelper() {
        jsonParser = new JSONParser();
        mHTTPClient = new HTTPClient();
    }

    public List<Room> getRoomList() throws IOException, JSONException {
        final String roomsInfo = mHTTPClient.getDBInfo(Addresses.ROOMS);
        if (!"".equals(roomsInfo)) {
            return jsonParser.parseRoomsInfo(roomsInfo);
        } else {
            return null;
        }
    }

    public List<Reservation> getReservationListById() throws IOException, JSONException {
        bookingsInfo = mHTTPClient.getDBInfo(Addresses.BOOKINGS);
        return jsonParser.parseBookingsInfo(bookingsInfo);
    }

    public void makeReservation(final String reservation) {
        mHTTPClient.setDBBookingsInfo(reservation);
    }

    public List<String> getUrlsList() throws IOException, JSONException {
        final String photosUrlsJson = mHTTPClient.getDBInfo(Addresses.PHOTOS);
        if (!"".equals(photosUrlsJson)) {
            return jsonParser.parsePhotoUrls(photosUrlsJson);
        } else {
            return null;
        }
    }
}
