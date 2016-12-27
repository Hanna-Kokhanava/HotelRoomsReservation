package com.hotel.hotelroomreservation.activities;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.hotel.hotelroomreservation.database.repo.RoomRepo;
import com.hotel.hotelroomreservation.dialogs.ErrorDialog;
import com.hotel.hotelroomreservation.dialogs.ErrorExitDialog;
import com.hotel.hotelroomreservation.model.Room;
import com.hotel.hotelroomreservation.utils.ErrorLogs;
import com.hotel.hotelroomreservation.utils.dropbox.DropboxHelper;
import com.hotel.hotelroomreservation.utils.validations.InternetValidation;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final int START_TAB_ID = 0;

    private RecyclerView mRecyclerView;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ProgressBar progressView;

    private ErrorLogs errorLogs;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fieldsInitialization();
        setUpNavigationView();
        new RoomsInfoAsyncTask().execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
        navigationView.getMenu().getItem(START_TAB_ID).setChecked(true);
    }

    private void fieldsInitialization() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.rooms_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        errorLogs = new ErrorLogs();

        progressView = (ProgressBar) findViewById(R.id.progress_bar);
        progressView.setVisibility(View.VISIBLE);
    }

    private class RoomsInfoAsyncTask extends AsyncTask<Void, String, List<Room>> {

        @Override
        protected List<Room> doInBackground(final Void... voids) {
            final RoomRepo roomRepo = new RoomRepo();
            List<Room> roomsInfo = null;
            String logs = "";

            if (new InternetValidation().isConnected(MainActivity.this)) {
                try {
                    roomsInfo = new DropboxHelper().getRoomList();
                } catch (final IOException | JSONException pE) {
                    logs = errorLogs.formLogs(pE);
                }

                if (roomsInfo != null) {
                    roomRepo.delete();
                    roomRepo.insert(roomsInfo);

                } else {
                    roomsInfo = roomRepo.selectAll();

                    if (roomsInfo == null) {
                        publishProgress(getString(R.string.server_problem), logs);
                    }
                }

            } else {
                roomsInfo = roomRepo.selectAll();

                if (roomsInfo == null) {
                    publishProgress(getString(R.string.internet_switch_on));
                }
            }

            return roomsInfo;
        }

        @Override
        protected void onProgressUpdate(final String... errors) {
            if (errors.length > 1) {
                new ErrorExitDialog(MainActivity.this, errors);
            } else {
                new ErrorDialog(MainActivity.this, errors[0]);
            }
        }

        @Override
        protected void onPostExecute(final List<Room> roomsInfo) {
            if (roomsInfo != null) {
                progressView.setVisibility(View.GONE);

                final RecyclerView.Adapter mAdapter = new RoomAdapter(roomsInfo, new RoomAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(final Room room, final int color) {
                        final Intent intent = new Intent(MainActivity.this, RoomActivity.class);
                        intent.putExtra(Constants.ROOM_INTENT_KEY, room);
                        intent.putExtra(Constants.COLOR_INTENT_KEY, color);
                        startActivity(intent);
                    }
                });

                mRecyclerView.setAdapter(mAdapter);
            }
        }
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.tab_home:
                        drawer.closeDrawers();
                        return true;
                    case R.id.tab_search:
//                        startActivity(new Intent(MainActivity.this, RoomFinderActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.tab_gallery:
                        startActivity(new Intent(MainActivity.this, PhotoGalleryActivity.class));
                        drawer.closeDrawers();
                        return true;
                }
                menuItem.setChecked(true);
                return true;
            }
        });

        final ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_app:
                startActivity(new Intent(this, AboutAppActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
