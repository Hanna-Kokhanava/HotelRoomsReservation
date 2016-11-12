package com.hotel.hotelroomreservation.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.adapters.PhotoAdapter;
import com.hotel.hotelroomreservation.model.Addresses;
import com.hotel.hotelroomreservation.loader.BitmapManager;

import java.util.ArrayList;
import java.util.List;

public class PhotoListActivity extends AppCompatActivity {
    private final static String PHOTOS_KEY = "photos";
    private Firebase dbReference;
    private RecyclerView.Adapter bitmapAdapter;
    private RecyclerView photosRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);

        toolbarInitialize();

        photosRecyclerView = (RecyclerView) findViewById(R.id.photos_recycler_view);
        photosRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbReference = new Firebase(Addresses.FIREBASE_URL);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final BitmapManager bitmapManager = new BitmapManager();
        final List<String> hotelPhotosUrls = new ArrayList<>();

        dbReference.child(PHOTOS_KEY).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot urlSnapshot : snapshot.getChildren()) {
                    hotelPhotosUrls.add((String) urlSnapshot.getValue());
                }
                bitmapAdapter = new PhotoAdapter(hotelPhotosUrls, bitmapManager);
                photosRecyclerView.setAdapter(bitmapAdapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.i("tag", firebaseError.getMessage().toString());
            }
        });
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

    private void toolbarInitialize() {
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        toolBar.setTitle(R.string.title_gallery);
        setSupportActionBar(toolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
