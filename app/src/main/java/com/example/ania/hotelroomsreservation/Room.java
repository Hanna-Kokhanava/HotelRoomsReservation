package com.example.ania.hotelroomsreservation;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ania on 18.09.2016.
 */
public class Room {

    @SerializedName("name")
    private String name;

    public Room(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
