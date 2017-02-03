package com.networklibrary.cache;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by Jayanth on 02-02-2017.
 */

public class FileCache {
    private static final String CACHE_DIRECTORY_PATH = Environment.getExternalStorageDirectory() + "/Pinotrest";
    protected File cacheDirectory;

    public FileCache(Context context) {
        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDirectory = new File(CACHE_DIRECTORY_PATH, "ImgCache");
        else
            cacheDirectory = context.getCacheDir();
        if (!cacheDirectory.exists())
            cacheDirectory.mkdirs();
    }

    public File getFile(String url) {
        String filename = String.valueOf(url.hashCode());
        File f = new File(cacheDirectory, filename);
        return f;

    }

    public void clear() {
        File[] files = cacheDirectory.listFiles();
        if (files == null)
            return;
        for (File f : files)
            f.delete();
    }
}
