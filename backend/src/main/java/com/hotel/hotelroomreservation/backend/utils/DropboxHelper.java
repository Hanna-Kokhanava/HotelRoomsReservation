package com.hotel.hotelroomreservation.backend.utils;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v1.DbxClientV1;
import com.hotel.hotelroomreservation.backend.constants.Constants;

import java.io.InputStream;

public class DropboxHelper {
    private static final String ACCESS_TOKEN = "KvdqeBk1vDAAAAAAAAAAJ5M2wHWLzC-a6UtuNYY2-RORoUMFBQ_quPPP2iFaSfO0";
    private static final String CLIENT_IDENTIFIER = "dropbox/hotelroomsreservation";

    private static final String JSON_EXTENSION = ".json";

    private static DbxRequestConfig config = new DbxRequestConfig(CLIENT_IDENTIFIER);
    private static DbxClientV1 client = new DbxClientV1(config, ACCESS_TOKEN);

    public static InputStream getFileInputStream(String fileName) {
        DbxClientV1.Downloader downloader = null;

        try {
            downloader = client.startGetFile(Constants.ROOT_DB_DIRECTORY + fileName + JSON_EXTENSION, null);
        } catch (DbxException e) {
            e.printStackTrace();
        }

        return downloader.body;
    }
}
