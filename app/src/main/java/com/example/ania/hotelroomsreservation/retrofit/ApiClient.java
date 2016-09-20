package com.example.ania.hotelroomsreservation.retrofit;

import android.util.Log;

import com.example.ania.hotelroomsreservation.model.Room;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ania on 18.09.2016.
 */
public class ApiClient {
    // With slash in the end!!!
    public static final String url = "http://192.168.1.100:8080/RoomReservation/getAllRooms/";
//    public static final String url = "http://10.31.164.209:8080/RoomReservation/getAllRooms/";

    public static void getData() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        ApiInterface info = retrofit.create(ApiInterface.class);
        Call<JsonArray> call = info.getRooms();
        call.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                Log.i("asd", "++++++++++++++");

                String jsonString = response.body().toString();
                Log.i("onResponse", jsonString);
                Type listType = new TypeToken<List<Room>>() {}.getType();
                List<Room> allRooms = new Gson().fromJson(jsonString, listType);
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.i("tag", "Get server response is failed");
            }
        });
    }
}
