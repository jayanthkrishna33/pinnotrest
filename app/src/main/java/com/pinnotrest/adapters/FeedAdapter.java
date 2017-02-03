package com.pinnotrest.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.networklibrary.ImageLoader.NetworkImageLoader;
import com.pinnotrest.R;
import com.pinnotrest.activity.ExploreFeedActivity;
import com.pinnotrest.model.Feed;

import java.util.ArrayList;

/**
 * Created by Jayanth on 02-02-2017.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.Holder> {
    private Context mContext;
    private ArrayList<Feed> mFeeds;
    private NetworkImageLoader mNetworkImageLoader;

    public FeedAdapter(Context context, ArrayList<Feed> feeds) {
        this.mContext = context;
        this.mFeeds = feeds;
        this.mNetworkImageLoader = NetworkImageLoader.getInstance(mContext);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(R.layout.feed_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Feed f = mFeeds.get(position);
        mNetworkImageLoader.loadImage(f.getmUrls().getFull(), R.drawable.feed_image_placeholder, holder.mFeedImageView);
        mNetworkImageLoader.loadImage(f.getmUser().getProfilePicture(), R.drawable.user_placeholder, holder.mUserImageView);
        holder.mUserNameTextView.setText(f.getmUser().getmUserName());
        holder.mNameTextView.setText(f.getmUser().getmName());
        holder.mLikesTextView.setText(f.getLikes());
        holder.mTagsTextView.setText(String.valueOf(f.getmCategory().size()));
        holder.mTimeTextView.setText(f.getCreatedAt());
        holder.mFeedImageView.setTag(position);
        if (f.getLikedByUser()) {
            holder.mLikeImageView.setImageResource(R.drawable.like_filled);
        } else {
            holder.mLikeImageView.setImageResource(R.drawable.like_grey);
        }
    }

    @Override
    public int getItemCount() {
        return mFeeds.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mUserNameTextView, mNameTextView, mTimeTextView, mLikesTextView, mTagsTextView;
        public ImageView mUserImageView, mFeedImageView, mLikeImageView;

        public Holder(View itemView) {
            super(itemView);
            mUserNameTextView = (TextView) itemView.findViewById(R.id.username_tv);
            mNameTextView = (TextView) itemView.findViewById(R.id.name_tv);
            mLikesTextView = (TextView) itemView.findViewById(R.id.likes_tv);
            mTagsTextView = (TextView) itemView.findViewById(R.id.tags_tv);
            mTimeTextView = (TextView) itemView.findViewById(R.id.date_tv);
            mUserImageView = (ImageView) itemView.findViewById(R.id.user_image_iv);
            mFeedImageView = (ImageView) itemView.findViewById(R.id.feed_image_iv);
            mLikeImageView = (ImageView) itemView.findViewById(R.id.like_image_view);

            mNameTextView.setOnClickListener(this);
            mTagsTextView.setOnClickListener(this);
            mLikesTextView.setOnClickListener(this);
            mFeedImageView.setOnClickListener(this);
            mLikeImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.feed_image_iv:
                    onFeedClick(mFeeds.get(Integer.parseInt(v.getTag().toString())));
                    break;
                case R.id.like_image_view: {
                    mLikeImageView.setImageResource(R.drawable.like_filled);
                    mLikesTextView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mLikeImageView.setImageResource(R.drawable.like_grey);
                            Snackbar.make(mLikeImageView, "Could't like this post", Snackbar.LENGTH_SHORT).show();
                        }
                    }, 1500);
                    break;
                }
            }
        }

        public void onFeedClick(Feed feed) {
            Intent in = new Intent(mContext, ExploreFeedActivity.class);
            in.putExtra("data", feed);
            mContext.startActivity(in);
        }
    }

    public void setFeeds(ArrayList<Feed> feeds) {
        this.mFeeds = feeds;
        notifyDataSetChanged();
    }

    public ArrayList<Feed> getFeeds() {
        return this.mFeeds;
    }


}
