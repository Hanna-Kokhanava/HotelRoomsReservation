package com.hotel.hotelroomreservation.utils.parsers;

import com.hotel.hotelroomreservation.constants.Addresses;
import com.hotel.hotelroomreservation.constants.Constants;
import com.hotel.hotelroomreservation.model.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParser {

    public List<Room> parseRoomsInfo(String roomsInfo) {
        List<Room> rooms = new ArrayList<>();
        Room room;

        try {
            JSONObject json = new JSONObject(roomsInfo);
            JSONArray array = json.getJSONArray(Addresses.ROOMS);

            for (int i = 0; i < array.length(); i++) {
                JSONObject roomObj = array.getJSONObject(i);

                room = new Room(roomObj.getString(Constants.NAME), roomObj.getInt(Constants.NUMBER),
                        roomObj.getInt(Constants.RATING), roomObj.getInt(Constants.VISITORS),
                        roomObj.getInt(Constants.PRICE), roomObj.getString(Constants.URL));
                rooms.add(room);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rooms;
    }
}
