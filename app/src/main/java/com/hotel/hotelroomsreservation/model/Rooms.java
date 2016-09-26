package com.hotel.hotelroomsreservation.model;

import java.util.List;

/**
 * Created by Ania on 25.09.2016.
 */
public class Rooms {
    private List<Room> allRooms;

    public Rooms() {

    }

    public List<Room> getAllRooms() {
        return allRooms;
    }

    public void setAllRooms(List<Room> allRooms) {
        this.allRooms = allRooms;
    }
}
