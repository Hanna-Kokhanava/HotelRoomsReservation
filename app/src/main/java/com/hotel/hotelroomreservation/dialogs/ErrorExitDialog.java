package com.hotel.hotelroomreservation.dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.hotel.hotelroomreservation.R;

public class ErrorExitDialog {

    public ErrorExitDialog(final Activity activity, final String[] errorMessages) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.DialogTheme);
        builder.setTitle(R.string.error_dialog);
        builder.setMessage(errorMessages[0]);
        builder.setIcon(R.drawable.ic_mood_bad_black_24dp);
        builder.setPositiveButton(R.string.exit_app, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                activity.finish();
            }
        });

        builder.setNegativeButton(R.string.send_email, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface pDialogInterface, final int pI) {
                final Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"anitacx88@gmail.com"});
                sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "HotelRoomsReservation app problem");
                sendIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                        "Probably, there are some problems with server!" + "\nLogs:" + errorMessages[1]);
                activity.startActivity(Intent.createChooser(sendIntent, "Send mail..."));
                activity.finish();
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }
}
