package com.example.ania.hotelroomsreservation.model;

import java.io.Serializable;

/**
 * Room object model
 * Created by Ania on 18.09.2016.
 */

public class Room{
    private String name;
    private int number;

    public Room() {

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
