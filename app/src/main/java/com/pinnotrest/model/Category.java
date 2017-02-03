package com.pinnotrest.model;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Jayanth on 02-02-2017.
 */

public class Category implements Serializable {
    public static final String TAG_SELF = "self";
    public static final String TAG_PHOTOS = "photos";

    private String id;
    private String title;
    private String photoCount;
    private HashMap<String, String> links;

    public Category(String id, String title, String photoCount, HashMap<String, String> links) {
        this.id = id;
        this.title = title;
        this.photoCount = photoCount;
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(String photoCount) {
        this.photoCount = photoCount;
    }

    public HashMap<String, String> getLinks() {
        return links;
    }

    public void setLinks(HashMap<String, String> links) {
        this.links = links;
    }

    public static Category fromJSON(JSONObject obj) {
        try {
            String id = obj.getString("id");
            String title = obj.getString("title");
            String photoCount = obj.getString("photo_count");
            String self = obj.getJSONObject("links").getString("self");
            String photos = obj.getJSONObject("links").getString("photos");
            HashMap map = new HashMap();
            map.put(TAG_SELF, self);
            map.put(TAG_PHOTOS, photos);
            return new Category(id, title, photoCount, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
