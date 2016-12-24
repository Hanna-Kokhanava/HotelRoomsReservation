package com.hotel.hotelroomreservation.loader;

import android.graphics.Bitmap;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class MemoryCache {
    private final Map<String, Bitmap> cache = Collections.synchronizedMap(new LinkedHashMap<String, Bitmap>());
    private long size;
    private final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 4);

    public Bitmap getBitmap(final String id) {
        try {
            if (!cache.containsKey(id)) {
                return null;
            }
            return cache.get(id);
        } catch (final NullPointerException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void putBitmap(final String id, final Bitmap bitmap) {
        try {
            if (cache.containsKey(id)) {
                size -= getSizeInBytes(cache.get(id));
            }
            cache.put(id, bitmap);
            size += getSizeInBytes(bitmap);
            checkSize();
        } catch (final Throwable th) {
            th.printStackTrace();
        }
    }

    private void checkSize() {
        if (size > maxMemory) {

            final Iterator<Map.Entry<String, Bitmap>> iter = cache.entrySet().iterator();
            while (iter.hasNext()) {
                final Map.Entry<String, Bitmap> entry = iter.next();
                size -= getSizeInBytes(entry.getValue());
                iter.remove();

                if (size <= maxMemory) {
                    break;
                }
            }
        }
    }

    public void clear() {
        try {
            cache.clear();
            size = 0;
        } catch (final NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    long getSizeInBytes(final Bitmap bitmap) {
        if (bitmap == null) {
            return 0;
        }
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}
