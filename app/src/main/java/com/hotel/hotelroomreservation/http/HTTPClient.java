package com.hotel.hotelroomreservation.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.hotel.hotelroomreservation.model.Addresses;
import com.hotel.hotelroomreservation.model.Currencies;
import com.hotel.hotelroomreservation.utils.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPClient implements Addresses {

    public static Bitmap getPhoto(String URL) {
        try {
            URL reqUrl = new URL(URL);
            HttpURLConnection connection = ((HttpURLConnection) reqUrl.openConnection());
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();

            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
