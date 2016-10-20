package com.hotel.hotelroomreservation.model;

/**
 * For testing gson library
 */
public class Rate {
    private String timestamp;
    private Quotes quotes;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Quotes getQuotes() {
        return quotes;
    }

    public void setQuotes(Quotes quotes) {
        this.quotes = quotes;
    }
}
