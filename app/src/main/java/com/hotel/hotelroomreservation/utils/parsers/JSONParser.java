package com.hotel.hotelroomreservation.utils.parsers;

import com.hotel.hotelroomreservation.constants.Addresses;
import com.hotel.hotelroomreservation.constants.Constants;
import com.hotel.hotelroomreservation.model.Reservation;
import com.hotel.hotelroomreservation.model.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParser {

    public List<Room> parseRoomsInfo(final String roomsInfo) throws JSONException {
        final List<Room> rooms = new ArrayList<>();
        final JSONObject json = new JSONObject(roomsInfo);
        final JSONArray array = json.getJSONArray(Addresses.ROOMS);

        JSONObject roomObj;
        Room room;

        for (int i = 0; i < array.length(); i++) {
            roomObj = array.getJSONObject(i);

            room = new Room(roomObj.getInt(Constants.NUMBER), roomObj.getString(Constants.NAME),
                    roomObj.getInt(Constants.RATING), roomObj.getInt(Constants.VISITORS),
                    roomObj.getInt(Constants.PRICE), roomObj.getString(Constants.URL));
            rooms.add(room);
        }

        return rooms;
    }

    public List<Reservation> parseBookingsInfo(final String bookingsInfo) throws JSONException {
        final List<Reservation> reservations = new ArrayList<>();
        final JSONObject json = new JSONObject(bookingsInfo);
        final JSONArray array = json.getJSONArray(Addresses.BOOKINGS);

        Reservation reservation;
        JSONObject bookingObj;

        for (int i = 0; i < array.length(); i++) {
            bookingObj = array.getJSONObject(i);

            reservation = new Reservation(bookingObj.getString(Constants.ARRIVAL), bookingObj.getString(Constants.DEPARTURE),
                    bookingObj.getString(Constants.EMAIL), bookingObj.getInt(Constants.ID), bookingObj.getString(Constants.NAME),
                    bookingObj.getString(Constants.NUMBER), bookingObj.getString(Constants.SURNAME));
            reservations.add(reservation);
        }

        return reservations;
    }

    public List<String> parsePhotoUrls(final String photoUrlsJson) throws JSONException {
        final List<String> urls = new ArrayList<>();

        final JSONObject json = new JSONObject(photoUrlsJson);
        final JSONArray array = json.getJSONArray(Addresses.PHOTOS);

        for (int i = 0; i < array.length(); i++) {
            urls.add(array.getString(i));
        }

        return urls;
    }

    public String parseToJson(final Reservation reservation, final String bookings) {
        final JSONObject obj = new JSONObject();

        try {
            final JSONObject json = new JSONObject(bookings);
            final JSONArray array = json.getJSONArray(Addresses.BOOKINGS);

            obj.put(Constants.ARRIVAL, reservation.getArrival());
            obj.put(Constants.DEPARTURE, reservation.getDeparture());
            obj.put(Constants.ID, reservation.getId());
            obj.put(Constants.EMAIL, reservation.getEmail());
            obj.put(Constants.NAME, reservation.getName());
            obj.put(Constants.SURNAME, reservation.getSurname());
            obj.put(Constants.NUMBER, reservation.getNumber());

            array.put(obj);

            return json.toString();

        } catch (final JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
