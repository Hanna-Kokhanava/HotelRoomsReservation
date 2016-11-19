package com.hotel.hotelroomreservation.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hotel.hotelroomreservation.App;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCache {
    private String directoryName = "images";
    private String filename;
    private File directory = App.getInstance().getApplicationContext().getDir(directoryName, Context.MODE_PRIVATE);

    public void putBitmap(Bitmap bitmap, String url) {
        filename = String.valueOf(url.hashCode() + ".png");
        File file = new File(directory, filename);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getBitmap(String url) {
        filename = String.valueOf(url.hashCode() + ".png");
        File file = new File(directory, filename);

        try {
            return BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
