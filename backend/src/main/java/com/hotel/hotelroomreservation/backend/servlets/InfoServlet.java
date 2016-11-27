package com.hotel.hotelroomreservation.backend.servlets;

import com.hotel.hotelroomreservation.backend.constants.Addresses;
import com.hotel.hotelroomreservation.backend.parsers.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InfoServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        URL url = new URL(Addresses.WIKI_URL);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
        StringBuilder text = new StringBuilder();
        String line;

        while (null != (line = br.readLine())) {
            text.append(line);
        }

        String parsedText = new JSONParser().parseWikiUrl(new String(text));
        resp.setContentType("text/plain");
        resp.getWriter().println(parsedText);
    }
}