package com.hotel.hotelroomreservation.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.hotel.hotelroomreservation.App;
import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.adapters.PhotoAdapter;
import com.hotel.hotelroomreservation.constants.Constants;
import com.hotel.hotelroomreservation.model.Room;
import com.hotel.hotelroomreservation.utils.firebase.FirebaseCallback;
import com.hotel.hotelroomreservation.utils.firebase.FirebaseHelper;
import com.hotel.hotelroomreservation.utils.validations.ContextHolder;

import java.util.ArrayList;
import java.util.List;

public class PhotoListActivity extends BaseActivity {
    private RecyclerView.Adapter bitmapAdapter;
    private RecyclerView photosRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);

        photosRecyclerView = (RecyclerView) findViewById(R.id.photos_recycler_view);
        photosRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseHelper firebaseHelper = new FirebaseHelper();
        firebaseHelper.getBitmapList(new FirebaseCallback.RoomInfoCallback<String>() {
            @Override
            public void onSuccess(List<String> bitmapsList) {
                bitmapAdapter = new PhotoAdapter(bitmapsList);
                photosRecyclerView.setAdapter(bitmapAdapter);
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
}
