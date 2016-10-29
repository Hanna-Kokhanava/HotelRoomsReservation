package com.hotel.hotelroomreservation.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.LruCache;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.utilities.Base64;
import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.http.HTTPClient;
import com.hotel.hotelroomreservation.model.Addresses;
import com.hotel.hotelroomreservation.model.Currencies;
import com.hotel.hotelroomreservation.threads.PhotosOperation;
import com.hotel.hotelroomreservation.utils.Contract;
import com.hotel.hotelroomreservation.utils.Presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

public class HomePageActivity extends AppCompatActivity implements Contract.Rates {
    private final static String PHOTOS_KEY = "photos";
    private Firebase dbReference;
    private ImageView hotelImageView;
    private ListView photosListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        toolbarInitialize();

        // Here just for checking
        new Presenter(this).onRatesRequest();

//        hotelImageView = (ImageView) findViewById(R.id.imageView);

        Firebase.setAndroidContext(this);
        dbReference = new Firebase(Addresses.FIREBASE_URL);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final PhotosOperation photosOperation = new PhotosOperation();
        final List<String> hotelPhotosUrls = new ArrayList<>();

        dbReference.child(PHOTOS_KEY).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot urlSnapshot : snapshot.getChildren()) {
                    hotelPhotosUrls.add((String) urlSnapshot.getValue());
                }
                setAdapter(hotelPhotosUrls, photosOperation);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void setAdapter(final List<String> urls, final PhotosOperation photosOperation) {
        String[] urlArray = urls.toArray(new String[urls.size()]);

        photosListView = (ListView) findViewById(R.id.photosListView);
        photosListView.setAdapter(new ArrayAdapter<String>(this, R.layout.image_adapter, R.id.text1, urlArray) {

            @Override
            public View getView(final int position, final View convertView, final ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
                photosOperation.drawBitmap(imageView, urls.get(position));
                return view;
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
