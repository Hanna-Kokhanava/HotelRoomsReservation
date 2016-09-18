package com.example.ania.hotelroomsreservation;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ania on 18.09.2016.
 */
public class ApiClient {
    public static final String url = "http://192.168.1.100:8181/RoomReservation/getAllRooms/";
    private static Retrofit retrofit = null;

    public static void getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        ApiInterface info = retrofit.create(ApiInterface.class);
        Call<String> call = info.getRooms();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("asd", response.body().toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("asd", "=============");
            }
        });
    }
}
