package com.hotel.hotelroomreservation.dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.model.Reservation;
import com.hotel.hotelroomreservation.model.Room;
import com.hotel.hotelroomreservation.utils.FirebaseHelper;

public class ConfirmationDialog {

    public ConfirmationDialog(final Activity activity, final Room room, final Reservation reservation) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.DialogTheme);
        builder.setTitle(R.string.confirmation_title);
        builder.setMessage(activity.getString(R.string.confirmation_text) + room.getName() + activity.getString(R.string.question));
        builder.setIcon(R.drawable.ic_hotel);

        String positiveText = activity.getString(R.string.OK);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseHelper.makeReservation(reservation);
                        activity.finish();
                    }
                });

        // TODO Cancel button

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
