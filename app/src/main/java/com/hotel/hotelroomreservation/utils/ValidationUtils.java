package com.hotel.hotelroomreservation.utils;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import java.util.Calendar;

public class ValidationUtils {

    public static boolean emailAddressValidation(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean calendarsValidation(Calendar arrivalCalendar, Calendar departureCalendar) {
        return arrivalCalendar.before(departureCalendar) && !arrivalCalendar.equals(departureCalendar);
    }

    public static boolean isNotEmpty(EditText arrivalValue, EditText departureValue) {
        return !isNotEmpty(arrivalValue) && !isNotEmpty(departureValue);
    }

    private static boolean isNotEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }
}
