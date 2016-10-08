package com.hotel.hotelroomreservation.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Room object model
 */

public class Room{
    private String name;
    private int number;
    private int price;
    private int visitors;
    private double rating;

    @JsonCreator
    public Room(@JsonProperty("name") String name, @JsonProperty("number") int number, @JsonProperty("price") int price,
                 @JsonProperty("rating") double rating, @JsonProperty("visitors") int visitors) {
        this.name = name;
        this.price = price;
        this.number = number;
        this.visitors = visitors;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getVisitors() {
        return visitors;
    }

    public void setVisitors(int visitors) {
        this.visitors = visitors;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
