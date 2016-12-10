package com.hotel.hotelroomreservation.dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.utils.dropbox.DropboxHelper;

public class ErrorBookingDialog {
    public ErrorBookingDialog(final Activity activity, final String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.DialogTheme);
        builder.setTitle(R.string.error_dialog);
        builder.setMessage(errorMessage);
        builder.setIcon(R.drawable.ic_mood_bad_black_24dp);
        builder.setPositiveButton(activity.getString(R.string.OK), null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
