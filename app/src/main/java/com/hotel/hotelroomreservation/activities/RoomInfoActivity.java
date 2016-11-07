package com.hotel.hotelroomreservation.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.model.Room;
import com.hotel.hotelroomreservation.utils.ContextHolder;

public class RoomInfoActivity extends AppCompatActivity {
    private Room room;
    private TextView roomName;
    private TextView roomRating;
    private TextView roomVisitors;
    private TextView roomPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_info);

        roomName = (TextView) findViewById(R.id.roomName);
        roomRating = (TextView) findViewById(R.id.roomRating);
        roomVisitors = (TextView) findViewById(R.id.roomVisitors);
        roomPrice = (TextView) findViewById(R.id.roomPrice);

        Bundle bundle = getIntent().getExtras();
        room = new Room();
        room = bundle.getParcelable("Room");

        roomName.setText(String.valueOf(ContextHolder.getContext().getResources().getString(R.string.room_name, room.getName())));
        roomRating.setText(String.valueOf(ContextHolder.getContext().getResources().getString(R.string.rating, room.getRating())));
        roomVisitors.setText(String.valueOf(ContextHolder.getContext().getResources().getString(R.string.guests, room.getVisitors())));
        roomPrice.setText(String.valueOf(ContextHolder.getContext().getResources().getString(R.string.price, room.getPrice())));

        toolbarInitialize();
    }

    private void toolbarInitialize() {
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        toolBar.setTitle(room.getName());
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
}
