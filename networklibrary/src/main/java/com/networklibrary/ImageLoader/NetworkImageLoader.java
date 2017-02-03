package com.networklibrary.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.networklibrary.Utils;
import com.networklibrary.cache.FileCache;
import com.networklibrary.cache.MemoryCache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jayanth on 02-02-2017.
 */

public class NetworkImageLoader {
    protected MemoryCache mMemoryCache = new MemoryCache();
    protected FileCache mFileCache;
    private ExecutorService mExecutorService;
    private Map<ImageView, String> mImagesMap = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    private static NetworkImageLoader sNetworkImageLoader;
    private int mPlaceHolderImage;
    private ImageQuality QUALITY;

    public static NetworkImageLoader getInstance(Context context) {
//        if (sNetworkImageLoader == null) {
//        sNetworkImageLoader = new NetworkImageLoader(context);
//        }
//        return sNetworkImageLoader;
        return new NetworkImageLoader(context);
    }

    private NetworkImageLoader(Context context) {
        mFileCache = new FileCache(context);
        mExecutorService = Executors.newFixedThreadPool(5);
        QUALITY = ImageQuality.MEDIUM;
    }

    protected boolean isIvReused(Image image) {
        String tag = mImagesMap.get(image.getImageView());
        if (tag == null || !tag.equals(image.getImageUrl()))
            return true;
        return false;
    }

    private Bitmap getBitmap(String url) {
        File f = mFileCache.getFile(url);
        Bitmap b = decodeFile(f);
        if (b != null)
            return b;
        try {
            Bitmap bitmap = null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is = conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            bitmap = decodeFile(f);
            return bitmap;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void loadImage(String url, int loader, ImageView imageView) {
        mPlaceHolderImage = loader;
        mImagesMap.put(imageView, url);
        Bitmap bitmap = mMemoryCache.get(url);
        if (bitmap != null)
            imageView.setImageBitmap(bitmap);
        else {
            queueImages(url, imageView);
            imageView.setImageResource(loader);
        }
    }

    private void queueImages(String url, ImageView imageView) {
        Image p = new Image(url, imageView);
        mExecutorService.submit(new ImageLoader(p));
    }

    private Bitmap decodeFile(File f) {
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            final int REQUIRED_SIZE = getImageSize(QUALITY);
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            if (QUALITY != ImageQuality.ORIGINAL)
                o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    class ImageLoader implements Runnable {
        Image photoToLoad;

        ImageLoader(Image photoToLoad) {
            this.photoToLoad = photoToLoad;
        }

        @Override
        public void run() {
            if (isIvReused(photoToLoad))
                return;
            Bitmap bmp = getBitmap(photoToLoad.getImageUrl());
            mMemoryCache.put(photoToLoad.getImageUrl(), bmp);
            if (isIvReused(photoToLoad))
                return;
            BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
            new Handler(Looper.getMainLooper()).post(bd);
        }
    }

    class BitmapDisplayer implements Runnable {
        private Bitmap mBitmap;
        private Image imageToLoad;

        public BitmapDisplayer(Bitmap bitmap, Image image) {
            this.mBitmap = bitmap;
            this.imageToLoad = image;
        }

        public void run() {
            if (isIvReused(imageToLoad))
                return;
            if (mBitmap != null) {
                imageToLoad.getImageView().post(new Runnable() {
                    @Override
                    public void run() {
                        imageToLoad.getImageView().setImageBitmap(mBitmap);
                    }
                });

            } else {
                imageToLoad.getImageView().post(new Runnable() {
                    @Override
                    public void run() {
                        imageToLoad.getImageView().setImageResource(mPlaceHolderImage);
                    }
                });
            }
        }
    }

    public void clearCache() {
        mMemoryCache.clear();
        mFileCache.clear();
    }

    private int getImageSize(ImageQuality q) {
        switch (q) {
            case ORIGINAL:
                return 200;
            case HIGH:
                return 700;
            case MEDIUM:
                return 200;
            case LOW:
                return 100;
        }
        return 200;
    }

    public void setImageQuality(ImageQuality quality) {
        QUALITY = quality;
    }

}
