package com.hotel.hotelroomreservation.utils;

import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import com.hotel.hotelroomreservation.R;

import org.w3c.dom.Text;

import java.util.Calendar;

public class ValidationUtils {

    public static boolean emailAddressValidation(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean calendarsValidation(Calendar arrivalCalendar, Calendar departureCalendar,
                                              EditText arrivalValue, EditText departureValue,
                                              TextInputLayout arrivalTextInput, TextInputLayout departureTextInput) {
        boolean flag = true;
        if (!isNotEmpty(arrivalValue)) {
            arrivalTextInput.setError("Can't by empty!");
            flag = false;
        }

        if (!isNotEmpty(departureValue)) {
            departureTextInput.setError("Can't by empty!");
            return false;
        }

        if (arrivalCalendar.after(departureCalendar)) {
            arrivalTextInput.setError("Arrival date can't be more than departure date!");
            flag = false;
        }

        if (arrivalCalendar.equals(departureCalendar)) {
            departureTextInput.setError("Departure date can't be equal with arrival date!");
            flag = false;
        }

        return flag;
    }

    public static boolean inputFieldsValidation(EditText name, EditText surname, EditText email, EditText phone) {
        boolean flag = true;

        if (!isNotEmpty(name)) {
            name.setError("Can't by empty!");
            flag = false;
        }
        if (!isNotEmpty(surname)) {
            surname.setError("Can't by empty!");
            flag = false;
        }
        if (!isNotEmpty(phone)) {
            phone.setError("Can't by empty!");
            flag = false;
        }
        if (!emailAddressValidation(email.getText().toString())) {
            email.setError("Not valid email address");
            flag = false;
        }
        return flag;
    }

    public static boolean isNotEmpty(EditText editText) {
        return !(editText.getText().toString().trim().length() == 0);
    }
}
