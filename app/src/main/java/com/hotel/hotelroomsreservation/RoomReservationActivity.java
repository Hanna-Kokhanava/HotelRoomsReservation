package com.hotel.hotelroomsreservation;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hotel.hotelroomsreservation.model.Room;
import com.hotel.hotelroomsreservation.model.Rooms;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class RoomReservationActivity extends AppCompatActivity {
    public static final String url = "http://192.168.1.100:8080/getAllRooms";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_reservation_activity);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        new HttpRequestTask().execute();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, List<Room>> {
        @Override
        protected List<Room> doInBackground(Void... params) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                Rooms response = restTemplate.getForObject(url, Rooms.class);
                List<Room> rooms = response.getAllRooms();
                Log.i("asd", rooms.get(0).getName() + " " + rooms.get(0).getNumber());
                Log.i("asd", rooms.get(1).getName() + " " + rooms.get(1).getNumber());
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

    }
}