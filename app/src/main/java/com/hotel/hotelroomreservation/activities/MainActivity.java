package com.hotel.hotelroomreservation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.adapters.RoomAdapter;
import com.hotel.hotelroomreservation.constants.Constants;
import com.hotel.hotelroomreservation.model.Room;
import com.hotel.hotelroomreservation.utils.firebase.FirebaseCallback;
import com.hotel.hotelroomreservation.utils.firebase.FirebaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private static final int START_TAB_ID = 0;

    private RecyclerView mRecyclerView;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ProgressBar progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fieldsInitialization();
    }

    private void fieldsInitialization() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.rooms_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressView = (ProgressBar) findViewById(R.id.progress_bar);
        progressView.setVisibility(View.VISIBLE);

        setUpNavigationView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        navigationView.getMenu().getItem(START_TAB_ID).setChecked(true);

        FirebaseHelper object = new FirebaseHelper();
        object.setFirebaseHelperListener(new FirebaseCallback<Room>() {
            @Override
            public void onSuccess(List<Room> roomsList) {
                setRoomList(roomsList);
            }
        });
    }

    private void setRoomList(List<Room> rooms) {
        progressView.setVisibility(View.GONE);

        RecyclerView.Adapter mAdapter = new RoomAdapter((ArrayList<Room>) rooms, new RoomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Room room) {
                Intent intent = new Intent(MainActivity.this, RoomActivity.class);
                intent.putExtra(Constants.ROOM_INTENT_KEY, room);
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.tab_home:
                        drawer.closeDrawers();
                        return true;
                    case R.id.tab_search:
                        startActivity(new Intent(MainActivity.this, RoomFinderActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.tab_gallery:
                        startActivity(new Intent(MainActivity.this, PhotoListActivity.class));
                        drawer.closeDrawers();
                        return true;
                }
                menuItem.setChecked(true);
                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_app:
                startActivity(new Intent(this, AboutAppActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
