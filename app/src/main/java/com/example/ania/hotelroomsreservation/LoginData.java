package com.example.ania.hotelroomsreservation;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ania on 15.09.2016.
 */
public class LoginData {

    LoginParser loginParser;
    String url = "http://10.0.2.2:8080/RoomReservation/register";

    public void getData(String name) {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("nm", name));
        loginParser.loginparse(list, url);
    }

}
