package com.hotel.hotelroomreservation.utils;

import java.util.List;

public interface FirebaseCallback <T> {
    void onSuccess(List<T> roomsList);
}
