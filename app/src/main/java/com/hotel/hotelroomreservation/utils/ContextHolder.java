package com.hotel.hotelroomreservation.utils;

import android.content.Context;

public enum ContextHolder {
    INSTANCE;

    private Context context;

    public static Context getContext() {
        return INSTANCE.context;
    }

    public static void setContext(Context context) {
        INSTANCE.context = context;
    }
}
