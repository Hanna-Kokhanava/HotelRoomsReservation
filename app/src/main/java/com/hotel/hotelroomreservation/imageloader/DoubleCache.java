package com.hotel.hotelroomreservation.imageloader;

import android.content.Context;
import android.graphics.Bitmap;

public class DoubleCache implements IMemoryCache {

    private final ImageCache mImageCache;
    private final DiskCache mDiskCache;

    public DoubleCache(final Context context) {
        mImageCache = new ImageCache();
        mDiskCache = new DiskCache(context);
    }

    @Override
    public void put(final String url, final Bitmap bitmap) {
        mImageCache.put(url, bitmap);
        mDiskCache.put(url, bitmap);
    }

    @Override
    public Bitmap get(final String url) {
        Bitmap bitmap = mImageCache.get(url);

        if (bitmap == null) {
            bitmap = mDiskCache.get(url);
        }
        return bitmap;
    }
}
