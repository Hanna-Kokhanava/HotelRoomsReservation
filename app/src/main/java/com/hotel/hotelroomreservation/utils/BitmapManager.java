package com.hotel.hotelroomreservation.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.hotel.hotelroomreservation.http.HTTPClient;
import com.hotel.hotelroomreservation.threads.ExecutingOperations;
import com.hotel.hotelroomreservation.threads.OnProgressCallback;
import com.hotel.hotelroomreservation.threads.OnResultCallback;
import com.hotel.hotelroomreservation.threads.ThreadManager;

import java.lang.ref.WeakReference;

public class BitmapManager {
    private ImageLoader imageLoader = new ImageLoader(ContextHolder.getContext());
    private ThreadManager threadManager = new ThreadManager();
    private BitmapOperation bitmapOperation = new BitmapOperation();

    public void setBitmap(final ImageView imageView, final String imageUrl) {
        threadManager.executeOperation(bitmapOperation, imageUrl, new BitmapResultCallback(imageUrl, imageView) {
            @Override
            public void onSuccess(final Bitmap bitmap) {
//                imageLoader.displayImage(imageUrl, imageView);
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
        private String value;

        public BitmapResultCallback(final String url, final ImageView imageView) {
            this.imageViewReference = new WeakReference<>(imageView);
            this.value = url;
            imageView.setTag(url);
        }

        @Override
        public void onSuccess(final Bitmap bitmap) {
            ImageView imageView = this.imageViewReference.get();
            if (imageView != null) {
                Object tag = imageView.getTag();
                if (tag != null && tag.equals(value)) {
                    imageView.setImageBitmap(bitmap);
                }
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
