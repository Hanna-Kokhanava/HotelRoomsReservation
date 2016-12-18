package com.hotel.hotelroomreservation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class AppWelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent(AppWelcomeActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}