package com.hotel.hotelroomreservation.backend.parsers;

import org.json.JSONObject;

public class JSONParser {

    public String parseWikiUrl(String str) {
        JSONObject json = new JSONObject(str);
        JSONObject query = json.getJSONObject("query");
        JSONObject pages = query.getJSONObject("pages");

        String key = pages.keySet().toString().substring(1, pages.keySet().toString().length() - 1);
        JSONObject page = pages.getJSONObject(key);

        return page.getString("extract");
    }
}
