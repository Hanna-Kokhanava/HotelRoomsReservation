package com.example.ania.hotelroomsreservation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RoomReservationActivity extends AppCompatActivity {
    EditText name;
    Button log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_reservation_activity);

        name = (EditText) findViewById(R.id.name);
        log = (Button) findViewById(R.id.log);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiClient.getClient();
            }
        });
    }
}