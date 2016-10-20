package com.hotel.hotelroomreservation.backend.servlets;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CurrencyServlet extends HttpServlet {
    private static final String ACCESS_KEY = "199cfad9da5bfc16bba963d56a55ab06";
    public static final String BASE_URL = "http://apilayer.net/api/";
    public static final String ENDPOINT = "live";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // TODO Make constants instead strings
        URL url = new URL(BASE_URL + ENDPOINT + "?access_key=" + ACCESS_KEY + "&currencies=PLN,EUR,BYR");

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

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

    }

}
