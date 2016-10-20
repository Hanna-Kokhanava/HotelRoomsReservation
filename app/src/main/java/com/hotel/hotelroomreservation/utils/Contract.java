package com.hotel.hotelroomreservation.utils;

import com.hotel.hotelroomreservation.model.Currencies;

public interface Contract {
    interface View {
        void showInfo(String response);
    }

    interface Rates {
        void showRates(Currencies currencies);
    }

    interface Presenter {
        void onReady();
        void onRatesRequest();
    }
}
