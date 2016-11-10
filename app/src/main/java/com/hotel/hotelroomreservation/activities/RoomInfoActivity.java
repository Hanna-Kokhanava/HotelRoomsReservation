package com.hotel.hotelroomreservation.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.model.Room;
import com.hotel.hotelroomreservation.utils.ContextHolder;

public class RoomInfoActivity extends AppCompatActivity {
    private Room room;
    private TextView roomName;
    private TextView roomVisitors;
    private TextView roomPrice;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_info);

        roomName = (TextView) findViewById(R.id.roomName);
        roomVisitors = (TextView) findViewById(R.id.roomVisitors);
        roomPrice = (TextView) findViewById(R.id.roomPrice);
        ratingBar = (RatingBar) findViewById(R.id.ratingStarBar);

        Bundle bundle = getIntent().getExtras();
        room = new Room();
        room = bundle.getParcelable("Room");

        roomName.setText(String.valueOf(ContextHolder.getContext().getResources().getString(R.string.room_name, room.getName())));
        roomVisitors.setText(String.valueOf(ContextHolder.getContext().getResources().getString(R.string.guests, room.getVisitors())));
        roomPrice.setText(String.valueOf(ContextHolder.getContext().getResources().getString(R.string.price, room.getPrice())));
        ratingBar.setRating(room.getRating());

        toolbarInitialize();
    }

    private void toolbarInitialize() {
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        toolBar.setTitle(room.getName() + " information");
        setSupportActionBar(toolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cancelSearch(View view) {
        finish();
    }

    public void openReservation(View view) {
        startActivity(new Intent(RoomInfoActivity.this, RoomReservationActivity.class));
    }
}
