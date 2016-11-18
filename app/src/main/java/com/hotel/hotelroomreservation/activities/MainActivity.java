package com.hotel.hotelroomreservation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

//TODO remove that activity
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        openHomeActivity();
    }

    private void openHomeActivity() {
        startActivity(new Intent(this, HomePageActivity.class));
        this.finish();
    }
}