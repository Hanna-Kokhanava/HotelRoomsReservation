package com.hotel.hotelroomreservation.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCache {
    private File sdPath;

    public FileCache() {
        sdPath = Environment.getExternalStorageDirectory();
        sdPath = new File(sdPath.getAbsolutePath() + "/" + "Images_cache");
        sdPath.mkdirs();
    }

    public void putBitmap(Bitmap bitmap, String url) {
        String filename = String.valueOf(url.hashCode() + ".png");
        File sdFile = new File(sdPath, filename);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(sdFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Bitmap getBitmap(String url) {
        String filename = String.valueOf(url.hashCode() + ".png");
        File sdFile = new File(sdPath, filename);

        try {
            return BitmapFactory.decodeStream(new FileInputStream(sdFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
