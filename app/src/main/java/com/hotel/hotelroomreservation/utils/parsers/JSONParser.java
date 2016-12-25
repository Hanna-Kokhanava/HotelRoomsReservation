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

    public List<Room> parseRoomsInfo(final String roomsInfo) {
        List<Room> rooms = null;
        Room room;

        final JSONObject json;
        try {
            json = new JSONObject(roomsInfo);
            final JSONArray array = json.getJSONArray(Addresses.ROOMS);
            rooms = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                final JSONObject roomObj = array.getJSONObject(i);

                room = new Room(roomObj.getInt(Constants.NUMBER), roomObj.getString(Constants.NAME),
                        roomObj.getInt(Constants.RATING), roomObj.getInt(Constants.VISITORS),
                        roomObj.getInt(Constants.PRICE), roomObj.getString(Constants.URL));
                rooms.add(room);
            }
        } catch (final JSONException e) {
            e.printStackTrace();
        }

        return rooms;
    }

    public List<Reservation> parseBookingsInfo(final String bookingsInfo) {
        List<Reservation> reservations = null;
        Reservation reservation;

        try {
            final JSONObject json = new JSONObject(bookingsInfo);
            final JSONArray array = json.getJSONArray(Addresses.BOOKINGS);
            reservations = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                final JSONObject bookingObj = array.getJSONObject(i);

                reservation = new Reservation(bookingObj.getString(Constants.ARRIVAL), bookingObj.getString(Constants.DEPARTURE),
                        bookingObj.getString(Constants.EMAIL), bookingObj.getInt(Constants.ID), bookingObj.getString(Constants.NAME),
                        bookingObj.getString(Constants.NUMBER), bookingObj.getString(Constants.SURNAME));
                reservations.add(reservation);
            }

        } catch (final JSONException e) {
            e.printStackTrace();
        }

        return reservations;
    }

    public List<String> parsePhotoUrls(final String photoUrlsJson) {
        List<String> urls = null;

        try {
            final JSONObject json = new JSONObject(photoUrlsJson);
            final JSONArray array = json.getJSONArray(Addresses.PHOTOS);
            urls = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                urls.add(array.getString(i));
            }

        } catch (final JSONException e) {
            e.printStackTrace();
        }

        return urls;
    }

    public String parseToJson(final Reservation reservation, final String bookings) {
        final JSONObject obj = new JSONObject();

        try {
            final JSONObject json = new JSONObject(bookings);
            final JSONArray array = json.getJSONArray(Addresses.BOOKINGS);

            obj.put("arrival", reservation.getArrival());
            obj.put("departure", reservation.getDeparture());
            obj.put("id", reservation.getId());
            obj.put("email", reservation.getEmail());
            obj.put("name", reservation.getName());
            obj.put("surname", reservation.getSurname());
            obj.put("number", reservation.getNumber());

            array.put(obj);

            return json.toString();

        } catch (final JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
