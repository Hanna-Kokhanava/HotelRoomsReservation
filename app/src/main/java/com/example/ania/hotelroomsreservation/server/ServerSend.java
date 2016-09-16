package com.example.ania.hotelroomsreservation.server;

import android.os.StrictMode;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

/**
 * Class for making requests and getting responses
 * Created by Ania on 15.09.2016.
 */
public class ServerSend {

    public static String connect(String name, int price) {
        String result = "";
        HttpURLConnection connection = null;

        try {
            connection = new ServerConnector().setConnection();

            // Do request to the server
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write("string=" + name);
            out.close();

            // Get response from the server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String decodedString;
            while ((decodedString = in.readLine()) != null) {
                result = decodedString;
            }
            in.close();

            Log.i("result", "============================" + result);

            return result;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }
}
