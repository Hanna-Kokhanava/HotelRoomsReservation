package com.hotel.hotelroomreservation.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.activities.MainActivity;
import com.hotel.hotelroomreservation.activities.RoomActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class RoomReservationFragment extends Fragment implements View.OnClickListener {
    private final Calendar currentCalendar = Calendar.getInstance();
    private Calendar arrivalCalendar;
    private Calendar departureCalendar;

    private View section1, section2;
    private View view;
    private TextInputLayout arrivalTextInput;
    private EditText arrivalValue;
    private EditText departureValue;
    private Button checkAvailability;

    public RoomReservationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_room_reservation, container, false);

        arrivalCalendar = Calendar.getInstance();
        departureCalendar = Calendar.getInstance();

        arrivalTextInput = (TextInputLayout) view.findViewById(R.id.arrival_textInput);
        arrivalTextInput.setErrorEnabled(true);
        arrivalValue = (EditText) view.findViewById(R.id.arrival_value);
        departureValue = (EditText) view.findViewById(R.id.departure_value);

        checkAvailability = (Button) view.findViewById(R.id.makeReservation);
        checkAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeReservation();
            }
        });
        arrivalValue.setOnClickListener(this);
        departureValue.setOnClickListener(this);

        return view;
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

        final DatePickerDialog pickerDialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        editText.setText(getString(R.string.default_date, day, month + 1, year));
                    }
                } ,currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.DAY_OF_MONTH));
        pickerDialog.setMinDate(currentCalendar);
        pickerDialog.show(getActivity().getFragmentManager(), "dialog");
    }


    @Override
    public void onStart() {
        super.onStart();
        section1 = view.findViewById(R.id.sectionRoomDetails);
        section2 = view.findViewById(R.id.sectionPersonalInfo);

        View header1 = view.findViewById(R.id.headerRoomDetails);
        header1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (section1.getVisibility() == View.GONE) {
                    section1.setVisibility(View.VISIBLE);
                    section2.setVisibility(View.GONE);
                } else {
                    section1.setVisibility(View.GONE);
                    section2.setVisibility(View.VISIBLE);
                }
            }
        });

        View header2 = view.findViewById(R.id.headerPersonalInfo);
        header2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (section2.getVisibility() == View.GONE) {
                    section2.setVisibility(View.VISIBLE);
                    section1.setVisibility(View.GONE);
                } else {
                    section2.setVisibility(View.GONE);
                    section1.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void makeReservation() {
        if (arrivalCalendar.before(departureCalendar) && !isEmpty(arrivalValue) && !isEmpty(departureValue)
                && !arrivalCalendar.equals(departureCalendar)) {

        } else {
            arrivalTextInput.setError(getString(R.string.invalid_date));
        }
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }
}
