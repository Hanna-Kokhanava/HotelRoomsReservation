package com.hotel.hotelroomreservation.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hotel.hotelroomreservation.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(final int layoutResID) {
        final View view = getLayoutInflater().inflate(layoutResID, null);
        toolbarInitialize(view);
        super.setContentView(view);
    }

    private void toolbarInitialize(final View view) {
        final Toolbar toolBar = (Toolbar) view.findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolBar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
