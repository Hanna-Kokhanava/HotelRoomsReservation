package com.hotel.hotelroomreservation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        openHomeActivity();
    }

    private void openHomeActivity() {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
        this.finish();
    }
}