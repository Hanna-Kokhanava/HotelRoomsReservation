package com.hotel.hotelroomreservation.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hotel.hotelroomreservation.R;

public class RoomFinderActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener {
    private static final int START_SEEKBAR_VALUE = 1;
    private TextView guestsTextView;
    private Button checkAvailability;
    private SeekBar seekBar;

//    private String RATE_URL = Addresses.SERVER_URL + Addresses.CURRENCY_RATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_finder);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(5);
        seekBar.setOnSeekBarChangeListener(this);

        checkAvailability = (Button) findViewById(R.id.searchButton);

        guestsTextView = (TextView) findViewById(R.id.guests_textView);
        guestsTextView.setText(getApplicationContext().getResources().getString(R.string.guests, START_SEEKBAR_VALUE));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        guestsTextView.setText(String.valueOf(getApplicationContext().getResources().getString(R.string.guests,
                seekBar.getProgress() + 1)));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        guestsTextView.setText(String.valueOf(getApplicationContext().getResources().getString(R.string.guests,
                seekBar.getProgress() + 1)));
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

    public void checkAvailability(View view) {

    }
}
