package com.hotel.hotelroomreservation.threads;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.hotel.hotelroomreservation.http.HTTPClient;

import java.lang.ref.WeakReference;

public class PhotosOperation {

    private ThreadManager threadManager = new ThreadManager();
    private BitmapOperation bitmapOperation = new BitmapOperation();

    public void drawBitmap(final ImageView imageView, final String imageUrl) {
        threadManager.executeOperation(bitmapOperation, imageUrl, new BitmapResultCallback(imageUrl, imageView) {
            @Override
            public void onSuccess(final Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
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

        private String value;
        private final WeakReference<ImageView> imageViewReference;

        public BitmapResultCallback(final String value, final ImageView imageView) {
            this.value = value;
            this.imageViewReference = new WeakReference<ImageView>(imageView);
            imageView.setTag(value);
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


    private class ImageData {
        String url;
        int w;
        int h;

        public ImageData(String url, int w, int h) {
            this.url = url;
            this.w = w;
            this.h = h;
        }
    }

}
