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
    private final DropboxHelper dropboxHelper = new DropboxHelper();
    @Override
    public void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        final InputStream inputStream = dropboxHelper.getFileInputStream(req.getParameter(Constants.FILE_NAME_PARAMETER));

        if (inputStream != null) {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            final StringBuilder text = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                text.append(line);
            }

            inputStream.close();
            bufferedReader.close();

            final JSONObject jsonObject = new JSONObject(new String(text));
            resp.setContentType("application/json");
            resp.getWriter().write(jsonObject.toString());

        } else {
            resp.setContentType("application/json");
            resp.getWriter().write("");
        }
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final DataInputStream inputStream = new DataInputStream(req.getInputStream());
        dropboxHelper.updateBookingsFile(inputStream);
    }
}
