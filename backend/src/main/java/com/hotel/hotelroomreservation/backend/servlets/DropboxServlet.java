package com.hotel.hotelroomreservation.backend.servlets;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v1.DbxClientV1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DropboxServlet extends HttpServlet {
    private static final String ACCESS_TOKEN = "KvdqeBk1vDAAAAAAAAAAJ5M2wHWLzC-a6UtuNYY2-RORoUMFBQ_quPPP2iFaSfO0";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
        DbxClientV1 client = new DbxClientV1(config, ACCESS_TOKEN);
        try {
            DbxClientV1.Downloader downloader = client.startGetFile("/Project/rooms.json", null);
            BufferedReader br = new BufferedReader(new InputStreamReader(downloader.body));
            StringBuilder text = new StringBuilder();
            String line;

            while (null != (line = br.readLine())) {
                text.append(line);
            }

            System.out.println(text);

        } catch (DbxException e) {
            e.printStackTrace();
        }
    }
}
