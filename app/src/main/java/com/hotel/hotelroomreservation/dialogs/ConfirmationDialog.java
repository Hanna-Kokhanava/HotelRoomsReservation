package com.hotel.hotelroomreservation.dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.model.Reservation;
import com.hotel.hotelroomreservation.model.Room;
import com.hotel.hotelroomreservation.utils.firebase.FirebaseHelper;

public class ConfirmationDialog {

    public ConfirmationDialog(final Activity activity, final Room room, final Reservation reservation) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.DialogTheme);
        builder.setTitle(R.string.confirmation_title);
        builder.setIcon(R.drawable.ic_hotel);
        builder.setMessage(activity.getString(R.string.room) + room.getName() + activity.getString(R.string.dialog_arrival)
                + reservation.getArrival() + activity.getString(R.string.dialog_departure)
                + reservation.getDeparture() + activity.getString(R.string.dialog_price)
                + room.getPrice() + activity.getString(R.string.dollar));

        builder.setPositiveButton(activity.getString(R.string.OK),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new FirebaseHelper().makeReservation(reservation);
                        activity.finish();
                    }
                });

        builder.setNegativeButton(R.string.cancel, null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
