package com.hotel.hotelroomreservation.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
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

public class RoomListActivity extends AppCompatActivity {
    private final static String ROOM_KEY = "rooms";
    private Firebase dbReference;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_list_activity);

        verifyStoragePermissions(this);

        toolbarInitialize();

        mRecyclerView = (RecyclerView) findViewById(R.id.rooms_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbReference = new Firebase(Addresses.FIREBASE_URL);
        dbReference.keepSynced(true);
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
                    // TODO Upload images from Storage
                    rooms.add(room);
                }

                progressView.setVisibility(View.GONE);

                if (mAdapter == null) {
                    mAdapter = new RoomAdapter((ArrayList<Room>) rooms, new RoomAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Room room) {
                            Intent intent = new Intent(RoomListActivity.this, RoomInfoActivity.class);
                            intent.putExtra("Room", room);
                            startActivity(intent);
                        }
                    });
                } else {
                    mAdapter.notifyDataSetChanged();
                }

                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(FirebaseError error) {
                Log.i("Firebase", "The read failed: " + error.getMessage());
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
