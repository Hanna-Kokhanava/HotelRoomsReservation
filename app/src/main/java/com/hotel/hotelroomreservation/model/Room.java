package com.hotel.hotelroomreservation.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Room implements Parcelable {
    private String name;
    private int number;
    private int rating;
    private int visitors;
    private String url;
    private int price;

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(final Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(final int size) {
            return new Room[size];
        }
    };

    public Room() {

    }

    protected Room(final Parcel in) {
        name = in.readString();
        number = in.readInt();
        rating = in.readInt();
        visitors = in.readInt();
        price = in.readInt();
        url = in.readString();
    }

    public Room(final int number, final String name, final int rating, final int visitors, final int price, final String url) {
        this.name = name;
        this.number = number;
        this.rating = rating;
        this.visitors = visitors;
        this.price = price;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(final int number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(final int price) {
        this.price = price;
    }

    public int getVisitors() {
        return visitors;
    }

    public void setVisitors(final int visitors) {
        this.visitors = visitors;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(final int rating) {
        this.rating = rating;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeString(name);
        parcel.writeInt(number);
        parcel.writeInt(rating);
        parcel.writeInt(visitors);
        parcel.writeInt(price);
        parcel.writeString(url);
    }
}
