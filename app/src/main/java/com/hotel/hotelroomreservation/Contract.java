package com.hotel.hotelroomreservation;

public interface Contract {
    interface View {
        void showInfo(String response);
    }

    interface Presenter {
        void onReady();
    }
}
