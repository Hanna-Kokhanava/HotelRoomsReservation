package com.hotel.hotelroomreservation.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hotel.hotelroomreservation.utils.ContextHolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCache {
    private final String directoryName = "images";
    private final File directory = ContextHolder.getInstance().getContext().getDir(directoryName, Context.MODE_PRIVATE);
    private String filename;

    public void putBitmap(final Bitmap bitmap, final String url) {
        filename = String.valueOf(url.hashCode() + ".png");
        final File file = new File(directory, filename);

        try {
            final FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getBitmap(final String url) {
        filename = String.valueOf(url.hashCode() + ".png");
        final File file = new File(directory, filename);

        try {
            if (file.exists()) {
                return BitmapFactory.decodeStream(new FileInputStream(file));
            }
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
