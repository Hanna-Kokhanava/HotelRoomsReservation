package com.hotel.hotelroomreservation.imageloader;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class DiskCache implements IMemoryCache {

    private static final String BITMAP_DIRECTORY = "bitmap";
    private static final int BYTES = 1024;
    private DiskLruCache mDiskLruCache;

    public DiskCache(final Context pContext) {
        try {
            final File cacheDir = getDiskCacheDir(pContext, BITMAP_DIRECTORY);

            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }

            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(pContext), 1, 10 * BYTES * BYTES);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(final String url, final Bitmap bitmap) {
        final String key = String.valueOf(url.hashCode());
        DiskLruCache.Editor editor = null;

        try {
            editor = mDiskLruCache.edit(key);

            if (editor == null) {
                return;
            }

            if (bitmapToStream(bitmap, editor.newOutputStream(0))) {
                mDiskLruCache.flush();
                editor.commit();
            } else {
                editor.abort();
            }
        } catch (final IOException e) {
            try {
                if (editor != null) {
                    editor.abort();
                }
            } catch (final IOException pE) {
                pE.printStackTrace();
            }
        }
    }

    @Override
    public Bitmap get(final String url) {
        final String key = String.valueOf(url.hashCode());
        final Bitmap bitmap = null;
        DiskLruCache.Snapshot snapshot = null;

        try {
            snapshot = mDiskLruCache.get(key);

            if (snapshot == null) {
                return null;
            }

            return BitmapFactory.decodeStream(snapshot.getInputStream(0));

        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (snapshot != null) {
                snapshot.close();
            }
        }

        return bitmap;
    }

    private Boolean bitmapToStream(final Bitmap bitmap, final OutputStream outputStream) throws IOException {
        try {
            return bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    private File getDiskCacheDir(final Context context, final String uniqueName) {
        final String cachePath;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getApplicationContext().getExternalCacheDir().getPath();
        } else {
            cachePath = context.getApplicationContext().getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    private int getAppVersion(final Context context) {
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;

        } catch (final PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
