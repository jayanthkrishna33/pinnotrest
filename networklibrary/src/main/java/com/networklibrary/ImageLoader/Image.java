package com.networklibrary.ImageLoader;

import android.widget.ImageView;

/**
 * Created by Jayanth on 02-02-2017.
 */

public class Image {
    private String mImageUrl;
    private ImageView mImageView;

    public Image(String mImageUrl, ImageView mImageView) {
        this.mImageUrl = mImageUrl;
        this.mImageView = mImageView;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public void setImageView(ImageView mImageView) {
        this.mImageView = mImageView;
    }
}
