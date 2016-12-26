package com.hotel.hotelroomreservation.utils;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

public class ErrorLogs {
    public String formLogs(final Exception e) {
        final CharArrayWriter cw = new CharArrayWriter();
        final PrintWriter w = new PrintWriter(cw);
        e.printStackTrace(w);
        w.close();
        return "\n" + cw;
    }
}
