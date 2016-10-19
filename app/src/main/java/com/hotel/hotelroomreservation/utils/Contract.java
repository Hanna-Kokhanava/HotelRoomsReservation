package com.hotel.hotelroomreservation.utils;

public interface Contract {
    interface View {
        void showInfo(String response);
    }

    interface Presenter {
        void onReady();
    }
}
