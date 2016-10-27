package com.hotel.hotelroomreservation.backend.servlets;

import com.hotel.hotelroomreservation.backend.model.Addresses;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CurrencyServlet extends HttpServlet {
    private static final String accessKeyString = "?access_key=";
    private static final String currenciesString = "&currencies=PLN,EUR,BYR";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        URL url = new URL(Addresses.BASE_URL + Addresses.ENDPOINT + accessKeyString + Addresses.ACCESS_KEY + currenciesString);

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
        StringBuilder text = new StringBuilder();
        String line;

        while (null != (line = br.readLine())) {
            text.append(line);
        }

        JSONObject jsonObject = new JSONObject(new String(text));
        resp.setContentType("application/json");
        resp.getWriter().write(jsonObject.toString());
    }
}
