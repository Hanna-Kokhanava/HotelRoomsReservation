package com.hotel.hotelroomreservation.loader;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.http.HTTPClient;
import com.hotel.hotelroomreservation.utils.ContextHolder;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoader {
    private final Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    private final MemoryCache memoryCache = new MemoryCache();
    private final FileCache fileCache;
    private final ExecutorService executorService;
    private final Handler handler = new Handler();

    public ImageLoader() {
        fileCache = new FileCache();
        executorService = Executors.newCachedThreadPool();
    }

    public void displayImage(final String url, final ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageDrawable(ContextCompat
                .getDrawable(ContextHolder.getInstance().getContext(), R.drawable.ic_photo_24dp));
        imageViews.put(imageView, url);

        final Bitmap bitmap = memoryCache.getBitmap(url);

        if (bitmap != null) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageBitmap(bitmap);
        } else {
            queuePhoto(url, imageView);
        }
    }

    private void queuePhoto(final String url, final ImageView imageView) {
        final PhotoToLoad p = new PhotoToLoad(url, imageView);
        executorService.submit(new PhotosLoader(p));
    }

    private Bitmap getBitmap(final String url) {
        Bitmap b = fileCache.getBitmap(url);
        if (b != null) {
            return b;
        }

        try {
            b = HTTPClient.getPhoto(url);

            if (b != null) {
                fileCache.putBitmap(b, url);
            }

            return b;

        } catch (final Throwable ex) {
            ex.printStackTrace();
            if (ex instanceof OutOfMemoryError) {
                memoryCache.clear();
            }
            return null;
        }
    }

    private class PhotoToLoad {
        private final String url;
        private final ImageView imageView;

        public PhotoToLoad(final String url, final ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }
    }

    class PhotosLoader implements Runnable {
        private final PhotoToLoad photoToLoad;

        PhotosLoader(final PhotoToLoad photoToLoad) {
            this.photoToLoad = photoToLoad;
        }

        @Override
        public void run() {
            try {
                if (imageViewReused(photoToLoad)) {
                    return;
                }
                final Bitmap bmp = getBitmap(photoToLoad.url);
                if (bmp == null) {
                    return;
                }

                memoryCache.putBitmap(photoToLoad.url, bmp);

                if (imageViewReused(photoToLoad)) {
                    return;
                }

                final BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
                handler.post(bd);

            } catch (final Throwable th) {
                th.printStackTrace();
            }
        }
    }

    boolean imageViewReused(final PhotoToLoad photoToLoad) {
        final String tag = imageViews.get(photoToLoad.imageView);
        return tag == null || !tag.equals(photoToLoad.url);
    }

    class BitmapDisplayer implements Runnable {
        private final Bitmap bitmap;
        private final PhotoToLoad photoToLoad;

        public BitmapDisplayer(final Bitmap b, final PhotoToLoad p) {
            bitmap = b;
            photoToLoad = p;
        }

        public void run() {
            if (imageViewReused(photoToLoad)) {
                return;
            }
            if (bitmap != null) {

                photoToLoad.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                photoToLoad.imageView.setImageBitmap(bitmap);
            }
        }
    }
}
