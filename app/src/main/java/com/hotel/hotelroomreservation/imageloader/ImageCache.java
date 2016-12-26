package com.hotel.hotelroomreservation.imageloader;

import android.graphics.Bitmap;
import android.util.LruCache;

public class ImageCache implements IMemoryCache {

    private static final int BYTES = 1024;
    private final LruCache<String, Bitmap> mBitmapLruCache;

    public ImageCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / BYTES);
        final int cacheSize = maxMemory / 8;

        mBitmapLruCache = new LruCache<String, Bitmap>(cacheSize) {

            @Override
            protected int sizeOf(final String key, final Bitmap bitmap) {
                return bitmap.getByteCount() / BYTES;
            }
        };
    }

    @Override
    public void put(final String url, final Bitmap bitmap) {
        final String key = String.valueOf(url.hashCode());
        mBitmapLruCache.put(key, bitmap);
    }

    @Override
    public Bitmap get(final String url) {
        final String key = String.valueOf(url.hashCode());
        return mBitmapLruCache.get(key);
    }
}
