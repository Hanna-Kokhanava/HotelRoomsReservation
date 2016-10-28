package com.hotel.hotelroomreservation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.model.Addresses;
import com.hotel.hotelroomreservation.model.Currencies;
import com.hotel.hotelroomreservation.threads.PhotosOperation;
import com.hotel.hotelroomreservation.threads.ThreadManager;
import com.hotel.hotelroomreservation.utils.Contract;
import com.hotel.hotelroomreservation.utils.Presenter;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity implements Contract.Rates {
    private final static String PHOTOS_KEY = "photos";
    private Firebase dbReference;
    private ImageView hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        toolbarInitialize();

        // Here just for checking
        new Presenter(this).onRatesRequest();

        hotel = (ImageView) findViewById(R.id.imageView);

        Firebase.setAndroidContext(this);
        dbReference = new Firebase(Addresses.FIREBASE_URL);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final ThreadManager threadManager = new ThreadManager();
        final PhotosOperation photosOperation = new PhotosOperation();
        final List<DataSnapshot> hotelPhotosUrls = new ArrayList<>();

        dbReference.child(PHOTOS_KEY).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot urlSnapshot : snapshot.getChildren()) {
                    hotelPhotosUrls.add(urlSnapshot);
                    photosOperation.drawBitmap(hotel, String.valueOf(urlSnapshot.getValue()));
                    return;
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;

            case R.id.about_app:
                startActivity(new Intent(this, AboutAppActivity.class));
                return true;

            case R.id.room_list:
                startActivity(new Intent(this, RoomsViewActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void toolbarInitialize() {
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    @Override
    public void showRates(Currencies currencies) {
        Log.i("rate", currencies.getUSDBYR() + " " + currencies.getUSDEUR() + " "
                + currencies.getUSDPLN() + " " + currencies.getTimestamp());
    }
}
