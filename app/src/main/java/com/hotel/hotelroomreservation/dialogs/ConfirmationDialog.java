package com.hotel.hotelroomreservation.dialogs;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.model.Reservation;
import com.hotel.hotelroomreservation.model.Room;
import com.hotel.hotelroomreservation.utils.FirebaseHelper;

public class ConfirmationDialog {

    public ConfirmationDialog(final Activity activity, final Room room, final Reservation reservation) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.DialogTheme);
        builder.setTitle("Booking confirmation");
        builder.setMessage("Do you really want to book " + room.getName() + "?");
        builder.setIcon(R.drawable.ic_hotel);

        String positiveText = activity.getString(R.string.OK);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseHelper.makeReservation(reservation);
                        activity.finish();
//                        Intent intent = new Intent(Intent.ACTION_SENDTO);
//                        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//                        intent.putExtra(Intent.EXTRA_EMAIL, "anitacx88@gmail.com");
//                        intent.putExtra(Intent.EXTRA_SUBJECT, "Asdfg");
//                        if (intent.resolveActivity(activity.getPackageManager()) != null) {
//                            activity.startActivity(intent);
//                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
