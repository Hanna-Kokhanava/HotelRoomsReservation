package com.example.ania.hotelroomsreservation.retrofit;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Retrofit API interface
 * Created by Ania on 18.09.2016.
 */
public interface ApiInterface {
    @GET("/getAllRooms")
    Call<JsonArray> getRooms();
}
