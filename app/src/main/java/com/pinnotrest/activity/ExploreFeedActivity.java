package com.pinnotrest.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.networklibrary.ImageLoader.ImageQuality;
import com.networklibrary.ImageLoader.NetworkImageLoader;
import com.pinnotrest.R;
import com.pinnotrest.adapters.CategoryAdapter;
import com.pinnotrest.model.Feed;

public class ExploreFeedActivity extends BaseActivity {

    private Feed mFeed;
    private ImageView mImageView, mUserImageView, mLikeImageView;
    private NetworkImageLoader mNetworkImageLoader;
    private TextView mLikeTextView, mTagsTextView, mUsernameTextView, mNameTextView;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFeed = (Feed) getIntent().getSerializableExtra("data");
        mNetworkImageLoader = NetworkImageLoader.getInstance(this);
        mNetworkImageLoader.setImageQuality(ImageQuality.HIGH);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mUserImageView = (ImageView) findViewById(R.id.user_image_iv);
        mUsernameTextView = (TextView) findViewById(R.id.username_tv);
        mNameTextView = (TextView) findViewById(R.id.name_tv);
        mLikeTextView = (TextView) findViewById(R.id.likes_tv);
        mTagsTextView = (TextView) findViewById(R.id.tags_tv);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mLikeImageView = (ImageView) findViewById(R.id.like_image_view);
        mNetworkImageLoader.loadImage(mFeed.getmUrls().getRaw(), R.drawable.feed_image_placeholder, mImageView);
        mNetworkImageLoader.loadImage(mFeed.getmUser().getProfilePicture(), R.drawable.user_placeholder, mUserImageView);
        mUsernameTextView.setText(mFeed.getmUser().getmUserName());
        mNameTextView.setText(mFeed.getmUser().getmName());
        mLikeTextView.setText(mFeed.getLikes());
        mTagsTextView.setText(String.valueOf(mFeed.getmCategory().size()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (mFeed.getmCategory() != null && mFeed.getmCategory().size() > 0) {
            mRecyclerView.setAdapter(new CategoryAdapter(this, mFeed.getmCategory()));
        }
        if (mFeed.getLikedByUser()) {
            mLikeImageView.setImageResource(R.drawable.like_filled);
        } else {
            mLikeImageView.setImageResource(R.drawable.like_grey);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getInfoSnackBar(mRecyclerView, "Doubble Tap or Drag Image with two fingers to Zoom", true).show();
            }
        }, 1000);

        setTitle(mFeed.getmUser().getmName() + "'s Feed");
    }

}
