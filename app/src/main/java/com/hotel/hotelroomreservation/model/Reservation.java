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

    public Reservation(final String arrival, final String departure, final String email, final int id,
                       final String name, final String number, final String surname) {
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

    public void setId(final int id) {
        this.id = id;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(final String arrival) {
        this.arrival = arrival;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(final String departure) {
        this.departure = departure;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}
