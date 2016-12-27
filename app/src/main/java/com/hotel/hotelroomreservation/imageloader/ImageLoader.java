package com.hotel.hotelroomreservation.imageloader;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;

import com.hotel.hotelroomreservation.App;
import com.hotel.hotelroomreservation.R;
import com.hotel.hotelroomreservation.http.HTTPClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoader {

    private IMemoryCache memoryCache;
    private final Handler handler = new Handler();
    private final ExecutorService mExecutorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void setMemoryCache(final IMemoryCache memoryCache) {
        this.memoryCache = memoryCache;
    }

    public void displayImage(final String url, final ImageView imageView, final IPaletteCallback listener) {
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageDrawable(ContextCompat
                .getDrawable(App.getContext(), R.drawable.ic_photo_24dp));

        final Bitmap bitmap = memoryCache.get(url);

        if (bitmap != null) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageBitmap(bitmap);

            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

                @Override
                public void onGenerated(final Palette palette) {
                    final Palette.Swatch vibrant = palette.getDarkMutedSwatch();
                    if (vibrant != null) {
                        listener.onSuccessGenerate(vibrant.getRgb());
                    } else {
                        listener.onSuccessGenerate(ContextCompat.getColor(App.getContext(), R.color.transparentBlack));
                    }
                }
            });
        }

        imageView.setTag(url.hashCode());
        final LoadingImage p = new LoadingImage(url, imageView);
        mExecutorService.submit(new ImagesLoader(p, listener));
    }

    public void displayImage(final String url, final ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageDrawable(ContextCompat
                .getDrawable(App.getContext(), R.drawable.ic_photo_24dp));

        final Bitmap bitmap = memoryCache.get(url);

        if (bitmap != null) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageBitmap(bitmap);
        }

        imageView.setTag(url.hashCode());
        final LoadingImage p = new LoadingImage(url, imageView);
        mExecutorService.submit(new ImagesLoader(p));
    }

    class ImagesLoader implements Runnable {

        private final LoadingImage loadingImage;
        private IPaletteCallback listener;

        public ImagesLoader(final LoadingImage loadingImage, final IPaletteCallback listener) {
            this.loadingImage = loadingImage;
            this.listener = listener;
        }

        public ImagesLoader(final LoadingImage loadingImage) {
            this.loadingImage = loadingImage;
        }

        @Override
        public void run() {
            final Bitmap bitmap = new HTTPClient().getPhoto(loadingImage.url);

            if (bitmap == null) {
                return;
            }

            if (listener != null) {
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

                    @Override
                    public void onGenerated(final Palette palette) {
                        final Palette.Swatch vibrant = palette.getDarkMutedSwatch();
                        if (vibrant != null) {
                            listener.onSuccessGenerate(vibrant.getRgb());
                        } else {
                            listener.onSuccessGenerate(ContextCompat.getColor(App.getContext(), R.color.transparentBlack));
                        }
                    }
                });
            }

            memoryCache.put(loadingImage.url, bitmap);
            handler.post(new ImageDisplayer(bitmap, loadingImage));
        }
    }

    private class LoadingImage {

        private final String url;
        private final ImageView imageView;

        public LoadingImage(final String url, final ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }
    }

    class ImageDisplayer implements Runnable {

        private final Bitmap mBitmap;
        private final LoadingImage mLoadingImage;

        public ImageDisplayer(final Bitmap bitmap, final LoadingImage image) {
            mBitmap = bitmap;
            mLoadingImage = image;
        }

        public void run() {
            if (mBitmap != null) {
                mLoadingImage.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mLoadingImage.imageView.setImageBitmap(mBitmap);
            }
        }
    }
}
