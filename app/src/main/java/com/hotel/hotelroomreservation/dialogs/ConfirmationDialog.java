package com.hotel.hotelroomreservation.dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.model.Reservation;
import com.hotel.hotelroomreservation.model.Room;
import com.hotel.hotelroomreservation.utils.dropbox.DropboxHelper;
import com.hotel.hotelroomreservation.utils.parsers.JSONParser;

public class ConfirmationDialog {

    public ConfirmationDialog(final Activity activity, final Room room,
                              final Reservation reservation, final String bookings) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.DialogTheme);
        builder.setTitle(R.string.confirmation_title);
        builder.setIcon(R.drawable.ic_hotel);
        builder.setMessage(activity.getString(R.string.room) + room.getName() + activity.getString(R.string.dialog_arrival)
                + reservation.getArrival() + activity.getString(R.string.dialog_departure)
                + reservation.getDeparture() + activity.getString(R.string.dialog_price)
                + room.getPrice() + activity.getString(R.string.dollar));

        builder.setPositiveButton(activity.getString(R.string.OK),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        final String reservationStr = new JSONParser().parseToJson(reservation, bookings);
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                new DropboxHelper().makeReservation(reservationStr);
                            }
                        }).start();
                        Toast.makeText(activity.getApplicationContext(), room.getName() + " has been successfully booked!", Toast.LENGTH_LONG).show();
                        activity.finish();
                    }
                });

        builder.setNegativeButton(R.string.cancel, null);

        final AlertDialog dialog = builder.create();
        dialog.show();
    }
}
