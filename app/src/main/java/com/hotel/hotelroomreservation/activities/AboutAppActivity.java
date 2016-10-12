package com.hotel.hotelroomreservation.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.hotel.hotelroomreservation.Contract;
import com.hotel.hotelroomreservation.Presenter;
import com.hotel.hotelroomreservation.R;

public class AboutAppActivity extends AppCompatActivity implements Contract.View {
    private Contract.Presenter presenter;
    private TextView viewInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewInfo = (TextView) findViewById(R.id.info_text);

        presenter = new Presenter(this);
        presenter.onReady();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showInfo(String response) {
        viewInfo.setText(response);
    }
}
