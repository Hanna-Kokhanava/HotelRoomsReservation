package com.hotel.hotelroomreservation.mvp;

public interface Contract {
    interface View {
        void showInfo(String response);
    }

    interface Presenter {
        void onReady();
    }
}
