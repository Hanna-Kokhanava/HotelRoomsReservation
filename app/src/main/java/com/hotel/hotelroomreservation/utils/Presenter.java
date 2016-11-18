package com.hotel.hotelroomreservation.utils;

import android.os.Handler;
import android.os.Looper;

import com.hotel.hotelroomreservation.http.HTTPClient;
import com.hotel.hotelroomreservation.model.Addresses;
import com.hotel.hotelroomreservation.model.Currencies;

//TODO that class to many work
public class Presenter implements Contract.Presenter {
    private String INFO_URL = Addresses.SERVER_URL + Addresses.APP_INFO;
    private String RATE_URL = Addresses.SERVER_URL + Addresses.CURRENCY_RATE;
    private Contract.View view;
    private Contract.Rates rates;
    private Handler handler;

    public Presenter(Contract.View view) {
        this.view = view;
        handler = new Handler(Looper.getMainLooper());
    }

    public Presenter(Contract.Rates rates) {
        this.rates = rates;
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onReady() {
        loadInfo();
    }

    @Override
    public void onRatesRequest() {
        loadRate();
    }


    private void loadRate() {
        new Thread() {
            @Override
            public void run() {
                Currencies rates = HTTPClient.getCurrentRate(RATE_URL);
                rateResponseInform(rates);
            }
        }.start();
    }

    private void rateResponseInform(final Currencies currencies) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                rates.showRates(currencies);
            }
        });
    }

    private void loadInfo() {
        new Thread() {
            @Override
            public void run() {
                String response = HTTPClient.getWikiResponse(INFO_URL);
                responseInform(response);
            }
        }.start();
    }

    private void responseInform(final String response) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                view.showInfo(response);
            }
        });
    }
}
