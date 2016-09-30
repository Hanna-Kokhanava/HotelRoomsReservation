package com.hotel.hotelroomsreservation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hotel.hotelroomsreservation.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        openHomeActivity();
    }

    private void openHomeActivity() {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
        this.finish();
    }
}