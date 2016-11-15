package com.hotel.hotelroomreservation.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FileCache {
    private File cacheDir;

    // TODO If there is no sd exception
    public FileCache(Context context) {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "Images_cache");
        }
        else {
            cacheDir = context.getCacheDir();
        }
    }

    public Bitmap getBitmap(Context context, String url) {
        String filename = String.valueOf(url.hashCode() + ".png");
        try {
            FileInputStream fileInputStream = context.openFileInput(filename);
            return BitmapFactory.decodeStream(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            f.delete();
        }
    }
}
