package com.hotel.hotelroomreservation;

import android.os.Looper;
import android.os.Handler;

import com.hotel.hotelroomreservation.http.HTTPClient;
import com.hotel.hotelroomreservation.model.Addresses;

public class Presenter implements Contract.Presenter {
    private String URL = Addresses.SERVER_URL + Addresses.APP_INFO;
    private Contract.View view;
    private Handler handler;

    public Presenter(Contract.View view) {
        this.view = view;
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onReady() {
        loadInfo();
    }

    private void loadInfo() {
        new Thread() {
            @Override
            public void run() {
                String response = HTTPClient.get(URL);
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
