package com.hotel.hotelroomreservation.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.constants.Addresses;

import java.util.Calendar;

public class RoomFinderActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    private final Calendar currentCalendar = Calendar.getInstance();
    private Calendar arrivalCalendar;
    private Calendar departureCalendar;

    private TextInputLayout arrivalTextInput;
    private TextView guestsTextView;
    private EditText arrivalValue;
    private EditText departureValue;
    private Button checkAvailability;
    private SeekBar seekBar;

    private String RATE_URL = Addresses.SERVER_URL + Addresses.CURRENCY_RATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_finder);

        arrivalCalendar = Calendar.getInstance();
        departureCalendar = Calendar.getInstance();

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(5);
        seekBar.setOnSeekBarChangeListener(this);

        checkAvailability = (Button) findViewById(R.id.searchButton);

        arrivalTextInput = (TextInputLayout) findViewById(R.id.arrival_textInput);
        arrivalTextInput.setErrorEnabled(true);
        arrivalValue = (EditText) findViewById(R.id.arrival_value);
        departureValue = (EditText) findViewById(R.id.departure_value);

        guestsTextView = (TextView) findViewById(R.id.guests_textView);
        guestsTextView.setText(getApplicationContext().getResources().getString(R.string.guests, "1"));

        arrivalValue.setOnClickListener(this);
        departureValue.setOnClickListener(this);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.arrival_value:
                setSelectedDate(arrivalValue, arrivalCalendar);
                break;
            case R.id.departure_value:
                setSelectedDate(departureValue, departureCalendar);
                break;
        }
    }

    private void setSelectedDate(final EditText editText, final Calendar calendar) {
        arrivalTextInput.setError(getString(R.string.empty_string));
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        editText.setText(getString(R.string.default_date, day, month + 1, year));
                    }
                }, currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
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
            if (arrivalCalendar.before(departureCalendar) && !isEmpty(arrivalValue) && !isEmpty(departureValue)) {
                int guestsNumber = seekBar.getProgress() + 1;

                // TODO Call method to find rooms - create new activity for list of rooms displaying
            } else {
                arrivalTextInput.setError(getString(R.string.invalid_date));
            }
    }

    public void cancelSearch(View view) {
        finish();
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }
}
