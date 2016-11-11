package com.hotel.hotelroomreservation.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

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

    public Bitmap getFile(String url) {
        String filename = String.valueOf(url.hashCode() + ".png");
        try {
            FileInputStream fis = ContextHolder.getContext().openFileInput(filename);
            Bitmap b = BitmapFactory.decodeStream(fis);
            fis.close();
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(String url, Bitmap bitmap) {

//        try {
//            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
//            String filename = String.valueOf(url.hashCode() + ".png");
//            File f = new File(
//                    Environment.getExternalStorageDirectory() + File.separator + filename);
//            f.createNewFile();
//            FileOutputStream out = new FileOutputStream(f);
//            out.write(bytes.toByteArray());
//            out.close();

            String filename = String.valueOf(url.hashCode() + ".png");
            FileOutputStream out;
            try {
                out = ContextHolder.getContext().openFileOutput(filename, Context.MODE_PRIVATE);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null)
            return;
        for (File f : files)
            f.delete();
    }
}
