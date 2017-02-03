package com.pinnotrest.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jayanth on 02-02-2017.
 */

public class Feed implements Serializable {
    private User mUser;
    private Urls mUrls;
    private ArrayList<Category> mCategory;
    private UserCollection mUserCollection;
    private Links mLinks;
    private String id, createdAt, width, height, color, likes, likedByUser;

    public Feed(User mUser, Urls mUrls, ArrayList<Category> mCategory, UserCollection mUserCollection, Links mLinks, String id, String createdAt, String width, String height, String color, String likes, String likedByUser) {
        this.mUser = mUser;
        this.mUrls = mUrls;
        this.mCategory = mCategory;
        this.mUserCollection = mUserCollection;
        this.mLinks = mLinks;
        this.id = id;
        this.createdAt = createdAt;
        this.width = width;
        this.height = height;
        this.color = color;
        this.likes = likes;
        this.likedByUser = likedByUser;
    }

    public User getmUser() {
        return mUser;
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }

    public Urls getmUrls() {
        return mUrls;
    }

    public void setmUrls(Urls mUrls) {
        this.mUrls = mUrls;
    }

    public ArrayList<Category> getmCategory() {
        return mCategory;
    }

    public void setmCategory(ArrayList<Category> mCategory) {
        this.mCategory = mCategory;
    }

    public UserCollection getmUserCollection() {
        return mUserCollection;
    }

    public void setmUserCollection(UserCollection mUserCollection) {
        this.mUserCollection = mUserCollection;
    }

    public Links getmLinks() {
        return mLinks;
    }

    public void setmLinks(Links mLinks) {
        this.mLinks = mLinks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public boolean getLikedByUser() {
        return Boolean.parseBoolean(this.likedByUser);
    }

    public void setLikedByUser(String likedByUser) {
        this.likedByUser = likedByUser;
    }

    public static Feed fromJSON(JSONObject o) {
        try {
            String id = o.getString("id");
            String created = o.getString("created_at");
            String width = o.getString("width");
            String height = o.getString("height");
            String color = o.getString("color");
            String likes = o.getString("likes");
            String likedByUser = o.getString("liked_by_user");
            User user = User.fromJSON(o.getJSONObject("user"));
            Urls urls = Urls.fromJSON(o.getJSONObject("urls"));
            Links links = Links.fromJSON(o.getJSONObject("links"));
            ArrayList<Category> cat = new ArrayList<>();
            JSONArray arr = o.getJSONArray("categories");
            for (int i = 0; i < arr.length(); i++) {
                cat.add(Category.fromJSON(arr.getJSONObject(i)));
            }
            return new Feed(user, urls, cat, new UserCollection(), links, id, created, width, height, color, likes, likedByUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
