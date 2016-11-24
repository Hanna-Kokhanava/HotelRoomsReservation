package com.hotel.hotelroomreservation.dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.activities.RoomListActivity;
import com.hotel.hotelroomreservation.model.Reservation;
import com.hotel.hotelroomreservation.utils.FirebaseHelper;

public class ConfirmationDialog {

    public ConfirmationDialog(final String title, final String content, final Reservation reservation, final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.DialogTheme);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setIcon(R.drawable.ic_hotel);

        String positiveText = "OK";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseHelper.makeReservation(reservation);
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
