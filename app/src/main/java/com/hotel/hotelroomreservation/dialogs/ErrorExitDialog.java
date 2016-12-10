package com.hotel.hotelroomreservation.dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.hotel.hotelroomreservation.R;

public class ErrorExitDialog {
    public ErrorExitDialog(final Activity activity, final String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.DialogTheme);
        builder.setTitle(R.string.error_dialog);
        builder.setMessage(errorMessage);
        builder.setIcon(R.drawable.ic_mood_bad_black_24dp);
        builder.setPositiveButton(R.string.exit_app, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(1);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
