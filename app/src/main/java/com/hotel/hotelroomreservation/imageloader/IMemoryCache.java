package com.hotel.hotelroomreservation.imageloader;

import android.graphics.Bitmap;

public interface IMemoryCache {

    void put(String url, Bitmap pBitmap);

    Bitmap get(String url);
}
