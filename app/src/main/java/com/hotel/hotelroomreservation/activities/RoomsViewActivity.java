package com.hotel.hotelroomreservation.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

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
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rooms_view_activity);

        mRecyclerView = (RecyclerView) findViewById(R.id.rooms_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        // Create class for working with Firebase
        Firebase.setAndroidContext(this);
        Firebase dbReference = new Firebase(Addresses.FIREBASE_URL);

        dbReference.child(ROOM_KEY).addValueEventListener(new ValueEventListener() {
            List<Room> rooms = new ArrayList<Room>();

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot roomSnapshot : snapshot.getChildren()) {
                    Room room = roomSnapshot.getValue(Room.class);
                    int i = Log.i(ROOM_KEY, room.getName() + " " + room.getPrice());
                    rooms.add(room);
                }

                mAdapter = new RoomAdapter((ArrayList<Room>) rooms, new RoomAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Room room) {
//                        Intent intent = new Intent(this, AboutAppActivity.class);
//                        startActivity(intent);
                        Toast.makeText(RoomsViewActivity.this, "================", Toast.LENGTH_SHORT).show();
//                        finish();
                    }
                });

                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(FirebaseError error) {
                System.out.println("The read failed: " + error.getMessage());
            }
        });
    }
}
