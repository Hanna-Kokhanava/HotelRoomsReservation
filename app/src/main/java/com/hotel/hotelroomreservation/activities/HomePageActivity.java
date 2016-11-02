package com.hotel.hotelroomreservation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.model.Currencies;
import com.hotel.hotelroomreservation.utils.ContextHolder;
import com.hotel.hotelroomreservation.utils.Contract;
import com.hotel.hotelroomreservation.utils.Presenter;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class HomePageActivity extends AppCompatActivity implements Contract.Rates {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        toolbarInitialize();

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_roomlist:
                        startActivity(new Intent(ContextHolder.getContext(), RoomListActivity.class));
                        break;
                    case R.id.tab_reservations:
//                        startActivity(new Intent(ContextHolder.getContext(), ));
                        break;
                    case R.id.tab_search:
//                        startActivity(new Intent(ContextHolder.getContext(), ));
                        break;
                    case R.id.tab_gallery:
                        startActivity(new Intent(ContextHolder.getContext(), PhotoListActivity.class));
                        break;
                }
            }
        });

        // Here just for checking
        new Presenter(this).onRatesRequest();
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
