package com.hotel.hotelroomreservation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.loader.ImageLoader;
import com.hotel.hotelroomreservation.model.Room;

public class RoomInfoActivity extends AppCompatActivity {
    private Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_info);

        TextView roomName = (TextView) findViewById(R.id.roomName);
        TextView roomVisitors = (TextView) findViewById(R.id.roomVisitors);
        TextView roomPrice = (TextView) findViewById(R.id.roomPrice);
        ImageView roomImage = (ImageView) findViewById((R.id.roomPhoto));
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingStarBar);

        Bundle bundle = getIntent().getExtras();
        room = new Room();
        room = bundle.getParcelable("Room");

        ImageLoader imageLoader = new ImageLoader();

        roomName.setText(String.valueOf(getApplicationContext().getResources().getString(R.string.room_name, room.getName())));
        roomVisitors.setText(String.valueOf(getApplicationContext().getResources().getString(R.string.guests, room.getVisitors())));
        roomPrice.setText(String.valueOf(getApplicationContext().getResources().getString(R.string.price, room.getPrice())));
        ratingBar.setRating(room.getRating());
        imageLoader.displayImage(room.getImageUrl(), roomImage);

        toolbarInitialize();
    }

    private void toolbarInitialize() {
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        toolBar.setTitle(room.getName() + getString(R.string.information));
        setSupportActionBar(toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
        }
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
