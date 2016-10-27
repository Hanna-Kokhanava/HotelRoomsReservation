package com.hotel.hotelroomreservation.http;

import com.firebase.client.DataSnapshot;
import com.hotel.hotelroomreservation.model.Addresses;
import com.hotel.hotelroomreservation.model.Currencies;
import com.hotel.hotelroomreservation.utils.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class HTTPClient implements Addresses {

    public static InputStream getPhoto(DataSnapshot URL) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            URL reqUrl = new URL(URL.getValue().toString());
            connection = ((HttpURLConnection) reqUrl.openConnection());
            connection.setRequestMethod("GET");
            inputStream = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return inputStream;
    }

    public static String getWikiResponse(String URL) {
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
            } finally {
                inputStream.close();
                reader.close();
                connection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return appInfo;
    }

    public static Currencies getCurrentRate(String URL) {
        String currencyRate = "";

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

                currencyRate = str.toString();
            } finally {
                inputStream.close();
                reader.close();
                connection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return JSONParser.parseCurrencyRate(currencyRate);
    }
}
