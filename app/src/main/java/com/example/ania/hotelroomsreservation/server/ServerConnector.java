package com.example.ania.hotelroomsreservation.server;

import android.os.StrictMode;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class provides the connection with server side using HttpURLConnection
 * Created by Ania on 16.09.2016.
 */
public class ServerConnector implements ControllerAddresses {
    private static final int TIMEOUT = 5000;
    private String serverUrl = ControllerAddresses.URL + ControllerAddresses.GET_ROOM_LIST;
    private HttpURLConnection connection = null;
    private URL url;

    public HttpURLConnection setConnection() {

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            url = new URL(serverUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(TIMEOUT);
            connection.setConnectTimeout(TIMEOUT);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
