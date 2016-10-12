/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.hotel.hotelroomreservation.backend.servlets;

import com.hotel.hotelroomreservation.backend.parsers.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InfoServlet extends HttpServlet {
    private final static String URL = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts&format=json&exsentences=7&exintro=&explaintext=&exsectionformat=plain&titles=Online_hotel_reservations";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        URL url = new URL(URL);
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

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("name");
        resp.setContentType("text/plain");
        if (name == null) {
            resp.getWriter().println("Please enter a name");
        }
        resp.getWriter().println("Hello " + name);
    }
}
