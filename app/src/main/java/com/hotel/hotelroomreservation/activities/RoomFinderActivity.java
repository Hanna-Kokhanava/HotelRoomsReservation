package com.hotel.hotelroomreservation.activities;

import android.app.DatePickerDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.utils.ContextHolder;
import com.hotel.hotelroomreservation.utils.Contract;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;

public class RoomFinderActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    private final Calendar currentCalendar = Calendar.getInstance();
    private Calendar arrivalCalendar;
    private Calendar departureCalendar;

    private TextView guestsTextView;
    private EditText arrivalValue;
    private EditText departureValue;
    private TextInputLayout arrivalTextInput;
    private TextInputLayout departureTextInput;

    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_finder);

        arrivalCalendar = Calendar.getInstance();
        departureCalendar = Calendar.getInstance();

        toolbarInitialize();

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(5);
        seekBar.setOnSeekBarChangeListener(this);

        arrivalTextInput = (TextInputLayout) findViewById(R.id.arrival_textInput);
        arrivalTextInput.setErrorEnabled(true);
        departureTextInput = (TextInputLayout) findViewById(R.id.departure_textInput);
        departureTextInput.setErrorEnabled(true);
        arrivalValue = (EditText) findViewById(R.id.arrival_value);
        departureValue = (EditText) findViewById(R.id.departure_value);
        guestsTextView = (TextView) findViewById(R.id.guests_textView);
        guestsTextView.setText(ContextHolder.getContext().getResources().getString(R.string.guests, "1"));

        arrivalValue.setOnClickListener(this);
        departureValue.setOnClickListener(this);

//        setDefaultDate();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        guestsTextView.setText(String.valueOf(ContextHolder.getContext().getResources().getString(R.string.guests,
                seekBar.getProgress() + 1)));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        guestsTextView.setText(String.valueOf(ContextHolder.getContext().getResources().getString(R.string.guests,
                seekBar.getProgress() + 1)));
    }

//    private void setDefaultDate() {
//        arrivalValue.setText(getString(R.string.default_date, currentCalendar.get(Calendar.DAY_OF_MONTH),
//                currentCalendar.get(Calendar.MONTH) + 1, currentCalendar.get(Calendar.YEAR)));
//    }

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

    private void toolbarInitialize() {
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            DateTime arrivalDate = new DateTime(arrivalCalendar.get(Calendar.YEAR), arrivalCalendar.get(Calendar.MONTH) + 1,
                    arrivalCalendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0, 0);
            DateTime departureDate = new DateTime(departureCalendar.get(Calendar.YEAR), departureCalendar.get(Calendar.MONTH) + 1,
                    departureCalendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0, 0);
            int guestsNumber = seekBar.getProgress() + 1;

            // TODO Call method to find rooms - create new activity for room displaying
            Log.i("tag", arrivalDate + " " + departureDate + " " + guestsNumber);
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
