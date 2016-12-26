package com.hotel.hotelroomreservation.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hotel.hotelroomreservation.App;
import com.hotel.hotelroomreservation.constants.Addresses;
import com.hotel.hotelroomreservation.constants.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public final class HTTPClient {
    private static final int RESPONSE_OK_CODE = 200;

    public Bitmap getPhoto(final String URL) {
        Bitmap bitmap = null;
        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            final URL reqUrl = new URL(URL);
            connection = ((HttpURLConnection) reqUrl.openConnection());
            connection.setDoInput(true);
            connection.connect();

            if (connection.getResponseCode() == RESPONSE_OK_CODE) {
                inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            }

        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    public String getDBInfo(final String fileName) {
        final String serverUrl = Addresses.SERVER_URL + Addresses.INFO;
        String jsonInfo = "";

        try {
            final URL url = new URL(serverUrl + Constants.FILE_NAME_PARAMETER + fileName);
            final HttpURLConnection connection = ((HttpURLConnection) url.openConnection());
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == RESPONSE_OK_CODE) {
                final InputStream inputStream = connection.getInputStream();
                final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                try {
                    final StringBuilder str = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        str.append(line);
                    }

                    jsonInfo = str.toString();

                } finally {
                    inputStream.close();
                    reader.close();
                    connection.disconnect();
                }
            }

        } catch (final IOException e) {
            e.printStackTrace();
        }

        return jsonInfo;
    }

    public void setDBBookingsInfo(final String reservation) {
        final String serverUrl = Addresses.SERVER_URL + Addresses.INFO + Constants.FILE_NAME_PARAMETER + Constants.BOOKING;
        final File file = new File(App.getContext().getFilesDir(),
                Constants.BOOKING + Constants.JSON_EXTENSION);

        try {
            final FileWriter writer = new FileWriter(file);
            writer.append(reservation);
            writer.flush();
            writer.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        try {
            final URL url = new URL(serverUrl);
            final HttpURLConnection connection = ((HttpURLConnection) url.openConnection());
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setFixedLengthStreamingMode(reservation.getBytes().length);

            final OutputStream outputStream = connection.getOutputStream();
            final FileInputStream inputStream = new FileInputStream(file);
            final byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.flush();
            inputStream.close();

            final PrintWriter out = new PrintWriter(outputStream);
            out.print(inputStream);
            out.close();

        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}