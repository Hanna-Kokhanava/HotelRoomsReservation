package com.hotel.hotelroomreservation.backend.servlets;

import com.hotel.hotelroomreservation.backend.constants.Constants;
import com.hotel.hotelroomreservation.backend.utils.DropboxHelper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DropboxServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        InputStream inputStream = DropboxHelper.getFileInputStream(req.getParameter(Constants.FILE_NAME_PARAMETER));

        if (inputStream != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder text = new StringBuilder();
            String line;

            while (null != (line = bufferedReader.readLine())) {
                text.append(line);
            }

            inputStream.close();
            bufferedReader.close();

            JSONObject jsonObject = new JSONObject(new String(text));
            resp.setContentType("application/json");
            resp.getWriter().write(jsonObject.toString());

        } else {
            resp.setContentType("application/json");
            resp.getWriter().write("");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DataInputStream inputStream = new DataInputStream(req.getInputStream());
        DropboxHelper.updateBookingsFile(inputStream);
    }
}
