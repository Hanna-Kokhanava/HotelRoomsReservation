package com.hotel.hotelroomreservation.fragments;

import android.os.AsyncTask;
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
import com.hotel.hotelroomreservation.database.repo.BookingsRepo;
import com.hotel.hotelroomreservation.dialogs.ConfirmationDialog;
import com.hotel.hotelroomreservation.dialogs.ErrorDialog;
import com.hotel.hotelroomreservation.dialogs.ErrorExitDialog;
import com.hotel.hotelroomreservation.model.Reservation;
import com.hotel.hotelroomreservation.model.Room;
import com.hotel.hotelroomreservation.utils.dropbox.DropboxHelper;
import com.hotel.hotelroomreservation.utils.validations.CalendarValidation;
import com.hotel.hotelroomreservation.utils.validations.InputValidation;
import com.hotel.hotelroomreservation.utils.validations.InternetValidation;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RoomBookingFragment extends Fragment implements View.OnClickListener {

    private static final String DIALOG = "dialog";

    private List<Date> arrivalDates;
    private Calendar[] reservationCalendars;
    private Room room;
    private String bookings;

    private DropboxHelper dropboxHelper;
    private InputValidation inputValidation;

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
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_room_booking, container, false);
        fieldsInitialization(view);

        new BookingsInfoAsyncTask().execute();

        return view;
    }

    private class BookingsInfoAsyncTask extends AsyncTask<Void, String, List<Reservation>> {

        @Override
        protected List<Reservation> doInBackground(final Void... voids) {
            final BookingsRepo bookingsRepo = new BookingsRepo();
            List<Reservation> reservations;

            if (new InternetValidation().isConnected(getActivity())) {
                reservations = dropboxHelper.getReservationListById();

                if (reservations != null) {
                    bookings = dropboxHelper.getBookingsInfo();
                    bookingsRepo.delete();
                    bookingsRepo.insert(reservations);

                } else {
                    reservations = bookingsRepo.selectAll();

                    if (reservations == null) {
                        publishProgress(getString(R.string.server_problem));
                    }
                }
            } else {
                reservations = bookingsRepo.selectAll();

                if (reservations == null) {
                    publishProgress(getString(R.string.internet_switch_on));
                }
            }

            return reservations;
        }

        @Override
        protected void onProgressUpdate(final String... errors) {
            new ErrorExitDialog(getActivity(), errors[0]);
        }

        @Override
        protected void onPostExecute(final List<Reservation> reservations) {
            if (reservations != null) {
                try {
                    setReservationDates(reservations);
                } catch (final ParseException p) {
                    new ErrorDialog(getActivity(), getString(R.string.parsing_error));
                }
            }
        }

        private void setReservationDates(final List<Reservation> reservations) throws ParseException {
            final List<Calendar> reservationDates = new ArrayList<>();
            final SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH);

            Date dateArrival;
            Date dateDeparture;

            for (final Reservation reservation : reservations) {
                if (room.getNumber() == reservation.getId()) {
                    dateArrival = sdf.parse(reservation.getArrival());
                    dateDeparture = sdf.parse(reservation.getDeparture());

                    arrivalDates.add(dateArrival);

                    final Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dateArrival);

                    while (calendar.getTime().before(dateDeparture)) {
                        final Calendar c = Calendar.getInstance();
                        c.setTime(calendar.getTime());
                        reservationDates.add(c);
                        calendar.add(Calendar.DATE, 1);
                    }
                }
            }

            final Calendar[] calendarDates = new Calendar[reservationDates.size()];
            reservationCalendars = reservationDates.toArray(calendarDates);
        }
    }

    private void fieldsInitialization(final View view) {
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

        final Bundle bundle = getActivity().getIntent().getExtras();
        room = new Room();
        room = bundle.getParcelable(Constants.ROOM_INTENT_KEY);

        arrivalDates = new ArrayList<>();

        dropboxHelper = new DropboxHelper();
        inputValidation = new InputValidation();

        final Button checkAvailability = (Button) view.findViewById(R.id.makeReservation);
        checkAvailability.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                makeReservation();
            }
        });

        arrivalValue.setOnClickListener(this);
        departureValue.setOnClickListener(this);
    }

    @Override
    public void onClick(final View view) {
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
            initializeDatePicker(new CalendarValidation().getSelectableDates(arrivalCalendar, arrivalDates), calendar, editText, true);
        } else {
            initializeDatePicker(reservationCalendars, calendar, editText, false);
        }
    }

    private void initializeDatePicker(final Calendar[] reservationDates, final Calendar calendar, final EditText editText, final boolean isDeparture) {
        arrivalTextInput.setError("");
        departureTextInput.setError("");

        final DatePickerDialog pickerDialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(final DatePickerDialog datePickerDialog, final int year, final int month, final int day) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        editText.setText(getString(R.string.default_date, year, month + 1, day));
                        departureValue.setEnabled(true);
                    }
                }, currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.DAY_OF_MONTH));
        pickerDialog.setMinDate(currentCalendar);

        final Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 1);
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
        pickerDialog.show(getActivity().getFragmentManager(), DIALOG);
    }

    public void makeReservation() {
        if (new InternetValidation().isConnected(getActivity())) {
            if (inputValidation.calendarsValidation(arrivalCalendar, departureCalendar, arrivalValue, departureValue, arrivalTextInput, departureTextInput)
                    && inputValidation.inputFieldsValidation(name, surname, email, phone)) {
                new ConfirmationDialog(getActivity(), room, formReservationObject(), bookings);
            }
        } else {
            new ErrorDialog(getActivity(), getActivity().getString(R.string.no_internet_warning));
        }
    }

    private Reservation formReservationObject() {
        final SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH);
        final String arrivalDate = format.format(arrivalCalendar.getTime());
        final String departureDate = format.format(departureCalendar.getTime());

        return new Reservation(arrivalDate, departureDate,
                email.getText().toString(), room.getNumber(), name.getText().toString(),
                phone.getText().toString(), surname.getText().toString());
    }
}
