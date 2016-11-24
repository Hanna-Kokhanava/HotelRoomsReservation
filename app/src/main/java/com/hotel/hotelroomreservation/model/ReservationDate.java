package com.hotel.hotelroomreservation.model;

import java.util.Date;

public class ReservationDate implements Comparable<ReservationDate>{
    private Date reservationDate;

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    @Override
    public int compareTo(ReservationDate reservationDate) {
        return getReservationDate().compareTo(reservationDate.getReservationDate());
    }
}
