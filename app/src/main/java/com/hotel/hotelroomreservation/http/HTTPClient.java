package com.hotel.hotelroomreservation.http;

import com.hotel.hotelroomreservation.model.Addresses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPClient implements Addresses {

    public static String get(String URL) {
        String appInfo = "";

        try {
            URL url = new URL(URL);
            HttpURLConnection connection = ((HttpURLConnection) url.openConnection());
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            try {
                StringBuilder str = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }

                appInfo = str.toString();
            }
            //TODO add finally block and in that block close all streams and close connection
            finally {
                inputStream.close();
                reader.close();
                connection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return appInfo;
    }
}
