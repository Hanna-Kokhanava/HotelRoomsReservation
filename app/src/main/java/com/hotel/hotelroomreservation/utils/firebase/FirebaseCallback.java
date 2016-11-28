package com.hotel.hotelroomreservation.utils.firebase;

import java.util.List;

public interface FirebaseCallback <T> {
    void onSuccess(List<T> roomsList);
}
