package com.hotel.hotelroomreservation.utils;

import com.hotel.hotelroomreservation.model.Currencies;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class JSONParser {
    public static Currencies parseCurrencyRate(String currencyRate) {
        String formattedDate = "";
        String PLNCurrency = "";
        String EURCurrency = "";
        String BYRCurrency = "";

        try {
            JSONObject json = new JSONObject(currencyRate);
            Date timeStampDate = new Date(json.getLong("timestamp") * 1000);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a", Locale.ENGLISH);

            formattedDate = dateFormat.format(timeStampDate);
            PLNCurrency = json.getJSONObject("quotes").getString("USDPLN");
            EURCurrency = json.getJSONObject("quotes").getString("USDEUR");
            BYRCurrency = json.getJSONObject("quotes").getString("USDBYR");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new Currencies(formattedDate, PLNCurrency, EURCurrency, BYRCurrency);
    }
}
