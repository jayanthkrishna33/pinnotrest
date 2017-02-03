package com.pinnotrest.model;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Jayanth on 02-02-2017.
 */

public class User implements Serializable {

    public static final String TAG_SMALL_IMAGE = "small";
    public static final String TAG_MEDIUM_IMAGE = "medium";
    public static final String TAG_LARGE_IMAGE = "large";
    public static final String TAG_LINK_SELF = "self";
    public static final String TAG_LINK_HTML = "html";
    public static final String TAG_LINK_PHOTOS = "photos";
    public static final String TAG_LINK_LIKES = "likes";

    private String mUserName;
    private String mName;
    private HashMap<String, String> mProfilePictures;
    private HashMap<String, String> mLinks;

    public User(String mUserName, String mName, HashMap mProfilePictures, HashMap mLinks) {
        this.mUserName = mUserName;
        this.mName = mName;
        this.mProfilePictures = mProfilePictures;
        this.mLinks = mLinks;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public HashMap getmProfilePictures() {
        return mProfilePictures;
    }

    public void setmProfilePictures(HashMap mProfilePictures) {
        this.mProfilePictures = mProfilePictures;
    }

    public HashMap getmLinks() {
        return mLinks;
    }

    public void setmLinks(HashMap mLinks) {
        this.mLinks = mLinks;
    }

    public static User fromJSON(JSONObject o) {
        try {
            String userName = o.getString("username");
            String name = o.getString("name");
            String id = o.getString("id");
            String small = o.getJSONObject("profile_image").getString("small");
            String medium = o.getJSONObject("profile_image").getString("medium");
            String large = o.getJSONObject("profile_image").getString("large");
            String self = o.getJSONObject("links").getString("self");
            String html = o.getJSONObject("links").getString("html");
            String photos = o.getJSONObject("links").getString("photos");
            String likes = o.getJSONObject("links").getString("likes");
            HashMap<String, String> map = new HashMap();
            map.put(TAG_SMALL_IMAGE, small);
            map.put(TAG_MEDIUM_IMAGE, medium);
            map.put(TAG_LARGE_IMAGE, large);

            HashMap<String, String> links = new HashMap();
            map.put(TAG_LINK_SELF, self);
            map.put(TAG_LINK_HTML, html);
            map.put(TAG_LINK_PHOTOS, photos);
            map.put(TAG_LINK_LIKES, likes);

            return new User(userName, name, map, links);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getProfilePicture() {
        return mProfilePictures.get(TAG_MEDIUM_IMAGE);
    }
}
