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
import com.hotel.hotelroomreservation.constants.Constants;
import com.hotel.hotelroomreservation.dialogs.ConfirmationDialog;
import com.hotel.hotelroomreservation.dialogs.ErrorBookingDialog;
import com.hotel.hotelroomreservation.model.Reservation;
import com.hotel.hotelroomreservation.model.Room;
import com.hotel.hotelroomreservation.utils.dropbox.DropboxCallback;
import com.hotel.hotelroomreservation.utils.dropbox.DropboxHelper;
import com.hotel.hotelroomreservation.utils.validations.CalendarValidation;
import com.hotel.hotelroomreservation.utils.validations.InputValidation;
import com.hotel.hotelroomreservation.utils.validations.InternetValidation;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RoomBookingFragment extends Fragment implements View.OnClickListener {
    private List<Date> arrivalDates;
    private Calendar[] reservationCalendars;
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

    public RoomBookingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_booking, container, false);
        fieldsInitialization(view);

        DropboxHelper dropboxHelper = new DropboxHelper();
        dropboxHelper.getReservationListById(new DropboxCallback.ReservationCallback<Date, Calendar>() {
            @Override
            public void onSuccess(List<Date> arrivalDatesList, List<Calendar> reservationDatesList) {
                arrivalDates = arrivalDatesList;

                Calendar[] calendarDates = new Calendar[reservationDatesList.size()];
                reservationCalendars = reservationDatesList.toArray(calendarDates);
            }
        }, room.getNumber());

        return view;
    }

    private void fieldsInitialization(View view) {
        arrivalCalendar = Calendar.getInstance();
        departureCalendar = Calendar.getInstance();

        arrivalTextInput = (TextInputLayout) view.findViewById(R.id.arrival_textInput);
        departureTextInput = (TextInputLayout) view.findViewById(R.id.departure_textInput);
        arrivalValue = (EditText) view.findViewById(R.id.arrival_value);
        departureValue = (EditText) view.findViewById(R.id.departure_value);
        departureValue.setEnabled(false);

        name = (EditText) view.findViewById(R.id.nameText);
        surname = (EditText) view.findViewById(R.id.surnameText);
        email = (EditText) view.findViewById(R.id.emailText);
        phone = (EditText) view.findViewById(R.id.phoneText);

        Bundle bundle = getActivity().getIntent().getExtras();
        room = new Room();
        room = bundle.getParcelable(Constants.ROOM_INTENT_KEY);

        arrivalDates = new ArrayList<>();

        Button checkAvailability = (Button) view.findViewById(R.id.makeReservation);
        checkAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeReservation();
            }
        });

        arrivalValue.setOnClickListener(this);
        departureValue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.arrival_value:
                getReservationsList(arrivalValue, arrivalCalendar, false);
                break;
            case R.id.departure_value:
                getReservationsList(departureValue, departureCalendar, true);
                break;
        }
    }

    private void getReservationsList(final EditText editText, final Calendar calendar, final boolean isDeparture) {
        if (isDeparture) {
            initializeDatePicker(CalendarValidation.getSelectableDates(arrivalCalendar, arrivalDates), calendar, editText, true);
        } else {
            initializeDatePicker(reservationCalendars, calendar, editText, false);
        }
    }

    private void initializeDatePicker(Calendar[] reservationDates, final Calendar calendar, final EditText editText, final boolean isDeparture) {
        arrivalTextInput.setError("");
        departureTextInput.setError("");

        final DatePickerDialog pickerDialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        editText.setText(getString(R.string.default_date, year, month + 1, day));
                        departureValue.setEnabled(true);
                    }
                }, currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.DAY_OF_MONTH));
        pickerDialog.setMinDate(currentCalendar);

        Calendar c = Calendar.getInstance();
        c.set(2020, 0, 1);
        pickerDialog.setMaxDate(c);

        if (isDeparture) {
            if (reservationDates.length != 0) {
                pickerDialog.setSelectableDays(reservationDates);
            } else {
                pickerDialog.setMinDate(arrivalCalendar);
            }
        } else {
            pickerDialog.setDisabledDays(reservationDates);
        }
        pickerDialog.show(getActivity().getFragmentManager(), "dialog");
    }

    public void makeReservation() {
        if (InternetValidation.isConnected(getActivity())) {
            if (InputValidation.calendarsValidation(arrivalCalendar, departureCalendar, arrivalValue, departureValue, arrivalTextInput, departureTextInput)
                    && InputValidation.inputFieldsValidation(name, surname, email, phone)) {
                new ConfirmationDialog(getActivity(), room, formReservationObject());
            }
        } else {
            new ErrorBookingDialog(getActivity(), getActivity().getString(R.string.no_internet_warning));
        }
    }

    private Reservation formReservationObject() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String arrivalDate = format.format(arrivalCalendar.getTime());
        String departureDate = format.format(departureCalendar.getTime());

        return new Reservation(arrivalDate, departureDate,
                email.getText().toString(), room.getNumber(), name.getText().toString(),
                phone.getText().toString(), surname.getText().toString());
    }
}
