package com.hotel.hotelroomreservation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hotel.hotelroomreservation.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //TODO you run RoomsViewActivity and HomePageActivity in the same time. It's not valid behaviour
        startActivity(new Intent(this, RoomsViewActivity.class));

        openHomeActivity();
    }

    private void openHomeActivity() {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
        this.finish();
    }
}