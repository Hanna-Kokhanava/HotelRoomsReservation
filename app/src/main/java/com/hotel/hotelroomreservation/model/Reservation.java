package com.hotel.hotelroomreservation.model;

public class Reservation {
    private int id;
    private String arrival;
    private String departure;
    private String name;
    private String surname;
    private String number;
    private String email;

    public Reservation() {

    }

    public Reservation(String arrival, String departure, String email, int id,
                       String name, String number, String surname) {
        this.id = id;
        this.arrival = arrival;
        this.departure = departure;
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
