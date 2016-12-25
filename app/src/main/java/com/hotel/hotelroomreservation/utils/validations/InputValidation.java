package com.hotel.hotelroomreservation.utils.validations;

import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import com.hotel.hotelroomreservation.App;
import com.hotel.hotelroomreservation.R;

import java.util.Calendar;

public final class InputValidation {

    public boolean calendarsValidation(final Calendar arrivalCalendar, final Calendar departureCalendar,
                                              final EditText arrivalValue, final EditText departureValue,
                                              final TextInputLayout arrivalTextInput, final TextInputLayout departureTextInput) {
        boolean flag = true;
        if (!isNotEmpty(arrivalValue)) {
            arrivalTextInput.setError(App.getContext().getString(R.string.validation_empty));
            flag = false;
        }

        if (!isNotEmpty(departureValue)) {
            departureTextInput.setError(App.getContext().getString(R.string.validation_empty));
            return false;
        }

        if (arrivalCalendar.after(departureCalendar)) {
            arrivalTextInput.setError(App.getContext().getString(R.string.more_validation));
            flag = false;
        }

        if (arrivalCalendar.equals(departureCalendar)) {
            departureTextInput.setError(App.getContext().getString(R.string.equal_validaton));
            flag = false;
        }

        return flag;
    }

    public boolean inputFieldsValidation(final EditText name, final EditText surname, final EditText email, final EditText phone) {
        boolean flag = true;

        if (!isNotEmpty(name)) {
            name.setError(App.getContext().getString(R.string.validation_empty));
            flag = false;
        }
        if (!isNotEmpty(surname)) {
            surname.setError(App.getContext().getString(R.string.validation_empty));
            flag = false;
        }
        if (!isNotEmpty(phone)) {
            phone.setError(App.getContext().getString(R.string.validation_empty));
            flag = false;
        }
        if (!emailAddressValidation(email.getText().toString())) {
            email.setError(App.getContext().getString(R.string.email_validation));
            flag = false;
        }
        return flag;
    }

    private boolean isNotEmpty(final EditText editText) {
        return !(editText.getText().toString().trim().isEmpty());
    }

    private boolean emailAddressValidation(final String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
