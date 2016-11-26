package com.hotel.hotelroomreservation.utils.validations;

import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import com.hotel.hotelroomreservation.App;
import com.hotel.hotelroomreservation.R;

import org.w3c.dom.Text;

import java.util.Calendar;

public class InputValidation {

    public static boolean emailAddressValidation(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean calendarsValidation(Calendar arrivalCalendar, Calendar departureCalendar,
                                              EditText arrivalValue, EditText departureValue,
                                              TextInputLayout arrivalTextInput, TextInputLayout departureTextInput) {
        boolean flag = true;
        if (!isNotEmpty(arrivalValue)) {
            arrivalTextInput.setError(App.getInstance().getString(R.string.validation_empty));
            flag = false;
        }

        if (!isNotEmpty(departureValue)) {
            departureTextInput.setError(App.getInstance().getString(R.string.validation_empty));
            return false;
        }

        if (arrivalCalendar.after(departureCalendar)) {
            arrivalTextInput.setError(App.getInstance().getString(R.string.more_validation));
            flag = false;
        }

        if (arrivalCalendar.equals(departureCalendar)) {
            departureTextInput.setError(App.getInstance().getString(R.string.equal_validaton));
            flag = false;
        }

        return flag;
    }

    public static boolean inputFieldsValidation(EditText name, EditText surname, EditText email, EditText phone) {
        boolean flag = true;

        if (!isNotEmpty(name)) {
            name.setError(App.getInstance().getString(R.string.validation_empty));
            flag = false;
        }
        if (!isNotEmpty(surname)) {
            surname.setError(App.getInstance().getString(R.string.validation_empty));
            flag = false;
        }
        if (!isNotEmpty(phone)) {
            phone.setError(App.getInstance().getString(R.string.validation_empty));
            flag = false;
        }
        if (!emailAddressValidation(email.getText().toString())) {
            email.setError(App.getInstance().getString(R.string.email_validation));
            flag = false;
        }
        return flag;
    }

    public static boolean isNotEmpty(EditText editText) {
        return !(editText.getText().toString().trim().length() == 0);
    }
}
