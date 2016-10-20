package com.hotel.hotelroomreservation.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.hotel.hotelroomreservation.model.Currencies;
import com.hotel.hotelroomreservation.model.Rate;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JSONParser {
    public static Currencies parseCurrencyRate(String currencyRate) {
        String formattedDate = "";
        String PLNCurrency = "";
        String EURCurrency = "";
        String BYRCurrency = "";

        try {
            JSONObject json = new JSONObject(currencyRate);
            Date timeStampDate = new Date((long) (json.getLong("timestamp") * 1000));
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");

            formattedDate = dateFormat.format(timeStampDate);
            PLNCurrency = json.getJSONObject("quotes").getString("USDPLN");
            EURCurrency = json.getJSONObject("quotes").getString("USDEUR");
            BYRCurrency = json.getJSONObject("quotes").getString("USDBYR");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new Currencies(formattedDate, PLNCurrency, EURCurrency, BYRCurrency);
    }

    public static Rate gsonParseCurrencyRate(String currencyRate) {
        Gson gson = new Gson();
        Rate rate = gson.fromJson(currencyRate, Rate.class);
        Log.i("rate", String.valueOf(rate));

        return rate;
    }
}
