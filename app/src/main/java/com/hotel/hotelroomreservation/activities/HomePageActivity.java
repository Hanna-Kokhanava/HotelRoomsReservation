package com.hotel.hotelroomreservation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hotel.hotelroomreservation.R;

public class HomePageActivity extends AppCompatActivity {
    private Toolbar toolBar;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        toolbarInitialize();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        setUpNavigationView();

        // Here just for checking
//        new Presenter(this).onRatesRequest();
    }

    @Override
    protected void onStart() {
        super.onStart();

        navigationView.getMenu().getItem(0).setChecked(true);
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.tab_home:
                        drawer.closeDrawers();
                        return true;
                    case R.id.tab_roomlist:
                        startActivity(new Intent(HomePageActivity.this, RoomListActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.tab_search:
                        startActivity(new Intent(HomePageActivity.this, RoomFinderActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.tab_gallery:
                        startActivity(new Intent(HomePageActivity.this, PhotoListActivity.class));
                        drawer.closeDrawers();
                        return true;
                }

                menuItem.setChecked(true);
                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolBar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
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
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;

            case R.id.about_app:
                startActivity(new Intent(this, AboutAppActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void toolbarInitialize() {
        toolBar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

    }

//    @Override
//    public void showRates(Currencies currencies) {
//        Log.i("rate", currencies.getUSDBYR() + " " + currencies.getUSDEUR() + " "
//                + currencies.getUSDPLN() + " " + currencies.getTimestamp());
//    }
}
