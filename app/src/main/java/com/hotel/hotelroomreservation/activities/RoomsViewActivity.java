package com.hotel.hotelroomreservation.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.config.Config;
import com.hotel.hotelroomreservation.model.Room;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class RoomsViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rooms_view_activity);

        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase(Config.FIREBASE_URL);

        ref.child("rooms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot roomSnapshot : snapshot.getChildren()) {
                    Room room = roomSnapshot.getValue(Room.class);
                    Log.i("room", room.getName() + " " + room.getPrice());
                    Toast.makeText(RoomsViewActivity.this, room.getName() + " " + room.getPrice(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
                System.out.println("The read failed: " + error.getMessage());
            }
        });
    }
}
