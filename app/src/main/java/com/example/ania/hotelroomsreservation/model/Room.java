package com.example.ania.hotelroomsreservation.model;

import com.google.gson.annotations.SerializedName;

/**
 * Room object model
 * Created by Ania on 18.09.2016.
 */
public class Room {
    @SerializedName("name")
    private String name;

    @SerializedName("number")
    private int number;

    public Room(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
