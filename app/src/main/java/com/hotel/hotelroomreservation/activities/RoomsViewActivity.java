package com.hotel.hotelroomreservation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.adapters.RoomAdapter;
import com.hotel.hotelroomreservation.model.Addresses;
import com.hotel.hotelroomreservation.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomsViewActivity extends AppCompatActivity {
    private final static String ROOM_KEY = "rooms";
    private Firebase dbReference;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rooms_view_activity);

        toolbarInitialize();

        mRecyclerView = (RecyclerView) findViewById(R.id.rooms_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Firebase.setAndroidContext(this);
        dbReference = new Firebase(Addresses.FIREBASE_URL);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final ProgressBar progressView = (ProgressBar) findViewById(R.id.progress_bar);
        progressView.setVisibility(View.VISIBLE);

        dbReference.child(ROOM_KEY).addValueEventListener(new ValueEventListener() {
            List<Room> rooms;

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                rooms = new ArrayList<>();
                for (DataSnapshot roomSnapshot : snapshot.getChildren()) {
                    Room room = roomSnapshot.getValue(Room.class);
                    rooms.add(room);
                }

                progressView.setVisibility(View.GONE);

                if (mAdapter == null) {
                    mAdapter = new RoomAdapter((ArrayList<Room>) rooms, new RoomAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Room room) {
                            startActivity(new Intent(RoomsViewActivity.this, RoomInfoActivity.class));
                        }
                    });
                } else {
                    mAdapter.notifyDataSetChanged();
                }

                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(FirebaseError error) {
                System.out.println("The read failed: " + error.getMessage());
                // TODO User friendly message about error
            }
        });
    }

    private void toolbarInitialize() {
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar_actionbar);
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
