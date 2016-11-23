package com.hotel.hotelroomreservation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.model.Reservation;
import com.hotel.hotelroomreservation.model.Room;
import com.hotel.hotelroomreservation.utils.FirebaseHelper;
import com.hotel.hotelroomreservation.utils.ValidationUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class RoomBookingFragment extends Fragment implements View.OnClickListener {
    private Room room;

    private final Calendar currentCalendar = Calendar.getInstance();
    private Calendar arrivalCalendar;
    private Calendar departureCalendar;

    private TextInputLayout arrivalTextInput;
    private TextInputLayout departureTextInput;
    private EditText arrivalValue;
    private EditText departureValue;
    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText phone;

    private Button checkAvailability;

    public RoomBookingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_booking, container, false);

        arrivalCalendar = Calendar.getInstance();
        departureCalendar = Calendar.getInstance();

        arrivalTextInput = (TextInputLayout) view.findViewById(R.id.arrival_textInput);
        departureTextInput = (TextInputLayout) view.findViewById(R.id.departure_textInput);
        arrivalValue = (EditText) view.findViewById(R.id.arrival_value);
        departureValue = (EditText) view.findViewById(R.id.departure_value);

        name = (EditText) view.findViewById(R.id.nameText);
        surname = (EditText) view.findViewById(R.id.surnameText);
        email = (EditText) view.findViewById(R.id.emailText);
        phone = (EditText) view.findViewById(R.id.phoneText);

        Bundle bundle = getActivity().getIntent().getExtras();
        room = new Room();
        room = bundle.getParcelable("Room");

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
        arrivalTextInput.setError("");
        departureTextInput.setError("");
        final DatePickerDialog pickerDialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        editText.setText(getString(R.string.default_date, day, month + 1, year));
                    }
                }, currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.DAY_OF_MONTH));
        pickerDialog.setMinDate(currentCalendar);
        pickerDialog.show(getActivity().getFragmentManager(), "dialog");
    }

    public void makeReservation() {
        if (ValidationUtils.calendarsValidation(arrivalCalendar, departureCalendar, arrivalValue, departureValue, arrivalTextInput, departureTextInput)
                && ValidationUtils.inputFieldsValidation(name, surname, email, phone)) {
            FirebaseHelper.makeReservation(formReservationObject());
            FirebaseHelper.getValue();
        }
    }

    private Reservation formReservationObject() {
        return new Reservation(String.valueOf(arrivalCalendar.getTimeInMillis()), String.valueOf(departureCalendar.getTimeInMillis()),
                email.getText().toString(), room.getNumber(), name.getText().toString(),
                phone.getText().toString(), surname.getText().toString());
    }
}
