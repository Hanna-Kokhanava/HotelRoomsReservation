package com.hotel.hotelroomreservation.model;

public class Currencies {
    private String USDPLN;
    private String USDEUR;
    private String USDBYR;
    private String timestamp;

    public Currencies(String timestamp, String USDPLN, String USDEUR, String USDBYR) {
        this.USDPLN = USDPLN;
        this.USDEUR = USDEUR;
        this.timestamp = timestamp;
        this.USDBYR = USDBYR;
    }

    public String getUSDPLN() {
        return USDPLN;
    }

    public void setUSDPLN(String USDPLN) {
        this.USDPLN = USDPLN;
    }

    public String getUSDEUR() {
        return USDEUR;
    }

    public void setUSDEUR(String USDEUR) {
        this.USDEUR = USDEUR;
    }

    public String getUSDBYR() {
        return USDBYR;
    }

    public void setUSDBYR(String USDBYR) {
        this.USDBYR = USDBYR;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
