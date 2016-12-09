package com.hotel.hotelroomreservation.utils.dropbox;

import java.util.List;

public interface DropboxCallback {

    interface RoomInfoCallback <T> {
        void onSuccess(List<T> dataList);
    }

    interface ReservationCallback<T1, T2> {
        void onSuccess(List<T1> arrivalDates, List<T2> reservationDates);
    }
}

