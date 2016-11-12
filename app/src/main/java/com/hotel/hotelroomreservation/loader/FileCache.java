package com.hotel.hotelroomreservation.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hotel.hotelroomreservation.utils.ContextHolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FileCache {

    private File cacheDir;

    public FileCache(Context context) {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "TTImages_cache");
        else
            cacheDir = context.getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();
    }

    public Bitmap getBitmap(String url) {
        String filename = String.valueOf(url.hashCode() + ".png");
        try {
            FileInputStream fileInputStream = ContextHolder.getContext().openFileInput(filename);
            Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null)
            return;
        for (File f : files)
            f.delete();
    }
}
