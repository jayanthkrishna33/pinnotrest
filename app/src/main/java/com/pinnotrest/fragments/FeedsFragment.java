package com.pinnotrest.fragments;

import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pinnotrest.R;
import com.pinnotrest.adapters.FeedAdapter;
import com.pinnotrest.handler.CloudHelper;
import com.pinnotrest.handler.PermissionHandler;
import com.pinnotrest.model.Feed;

import java.util.ArrayList;

/**
 * Created by Jayanth on 02-02-2017.
 */

public class FeedsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final int REQUEST_WRITE_STORAGE = 123;
    private RecyclerView mRecyclerView;
    private FeedAdapter mFeedAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FrameLayout mPlaceholder;
    private ImageView mPlaceholderImageView;
    private TextView mPlaceholderTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.feed_fragment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipedown_refresh_layout);
        mPlaceholder = (FrameLayout) rootView.findViewById(R.id.placeholder);
        mPlaceholderImageView = (ImageView) rootView.findViewById(R.id.placeholder_image);
        mPlaceholderTextView = (TextView) rootView.findViewById(R.id.placeholder_text);
        mPlaceholder.setVisibility(View.GONE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFeedAdapter = new FeedAdapter(getActivity(), new ArrayList<Feed>());
        mRecyclerView.setAdapter(mFeedAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        });
        return rootView;
    }

    @Override
    public void onRefresh() {
        if (PermissionHandler.hasFileReadPermission(getActivity())) {
            new GetFeed().execute();
        } else {
            PermissionHandler.requestFileReadPermission(getActivity(), REQUEST_WRITE_STORAGE);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private class GetFeed extends AsyncTask<Void, Void, Void> {
        ArrayList<Feed> feeds;

        @Override
        protected void onPreExecute() {
            mPlaceholderImageView.setImageResource(R.drawable.waiting);
            mPlaceholderTextView.setText("Loading feeds.");
            mPlaceholder.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            feeds = CloudHelper.getFeeds();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (feeds.size() > 0) {
                mFeedAdapter = new FeedAdapter(getActivity(), feeds);
                mRecyclerView.setAdapter(mFeedAdapter);
                mPlaceholder.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            } else {
                mPlaceholderImageView.setImageResource(R.drawable.sad);
                mPlaceholderTextView.setText("I got no feeds, please try again.");
                mPlaceholder.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onRefresh();
                } else {
                    Toast.makeText(getActivity(), "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

}
