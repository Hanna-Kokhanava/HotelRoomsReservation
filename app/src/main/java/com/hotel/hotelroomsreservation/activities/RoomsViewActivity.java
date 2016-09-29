package com.hotel.hotelroomsreservation.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hotel.hotelroomsreservation.R;
import com.hotel.hotelroomsreservation.model.Room;
import com.hotel.hotelroomsreservation.model.Rooms;
import com.hotel.hotelroomsreservation.model.ServerAddresses;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Ania on 29.09.2016.
 */
public class RoomsViewActivity extends AppCompatActivity {
    public static final String url = ServerAddresses.URL + ServerAddresses.GET_ALL_ROOMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rooms_view_activity);

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
                Log.i("Test", rooms.get(0).getName() + " " + rooms.get(0).getNumber());
                Log.i("Test", rooms.get(1).getName() + " " + rooms.get(1).getNumber());
                return rooms;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

    }
}
