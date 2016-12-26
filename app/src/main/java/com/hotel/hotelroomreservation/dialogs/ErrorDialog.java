package com.hotel.hotelroomreservation.dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.hotel.hotelroomreservation.R;

public class ErrorDialog {
    public ErrorDialog(final Activity activity, final String errorMessage) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.DialogTheme);
        builder.setTitle(R.string.error_dialog);
        builder.setMessage(errorMessage);
        builder.setIcon(R.drawable.ic_mood_bad_black_24dp);
        builder.setPositiveButton(activity.getString(R.string.OK), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                activity.finish();
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }
}
