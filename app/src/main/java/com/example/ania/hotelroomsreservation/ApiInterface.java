package com.example.ania.hotelroomsreservation;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Ania on 18.09.2016.
 */
public interface ApiInterface {
    @GET("/getAllRooms")
    Call<String> getRooms();
}
