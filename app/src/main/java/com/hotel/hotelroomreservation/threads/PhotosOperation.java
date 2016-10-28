package com.hotel.hotelroomreservation.threads;

import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;

import com.hotel.hotelroomreservation.http.HTTPClient;

import java.lang.ref.WeakReference;

public class PhotosOperation {
    public static final int MAX_MEMORY_FOR_IMAGES = 64 * 1000 * 1000;

    private ThreadManager threadManager = new ThreadManager();
    private BitmapOperation bitmapOperation = new BitmapOperation();
    private final LruCache<String, Bitmap> lruCache;

    public PhotosOperation() {
        this.lruCache = new LruCache<String, Bitmap>(Math.min((int) (Runtime.getRuntime().maxMemory() / 4), MAX_MEMORY_FOR_IMAGES)) {
            @Override
            protected int sizeOf(final String key, final Bitmap value) {
                return key.length() + value.getByteCount();
            }
        };
    }

    public void drawBitmap(final ImageView imageView, final String imageUrl) {
            final Bitmap bitmap = lruCache.get(imageUrl);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }

        threadManager.executeOperation(bitmapOperation, imageUrl, new BitmapResultCallback(imageView) {
            @Override
            public void onSuccess(final Bitmap bitmap) {
                if (bitmap != null) {
                    lruCache.put(imageUrl, bitmap);
                }
                super.onSuccess(bitmap);
            }
        });
    }

    private class BitmapOperation implements ExecutingOperations<String, Void, Bitmap> {
        @Override
        public Bitmap executing(String url, OnProgressCallback<Void> progressCallback) {
            return HTTPClient.getPhoto(url);
        }
    }

    private class BitmapResultCallback implements OnResultCallback<Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;

        public BitmapResultCallback(final ImageView imageView) {
            this.imageViewReference = new WeakReference<>(imageView);
        }

        @Override
        public void onSuccess(final Bitmap bitmap) {
            ImageView imageView = this.imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }

        @Override
        public void onError(final Exception e) {
            e.printStackTrace();
        }

        @Override
        public void onProgressChanged(final Void aVoid) {

        }
    }

}
