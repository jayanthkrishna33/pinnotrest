package com.pinnotrest.handler;

import com.networklibrary.http.HttpHelper;
import com.pinnotrest.AppConstants;
import com.pinnotrest.model.Feed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jayanth on 02-02-2017.
 */

public class CloudHelper {

    public static ArrayList<Feed> getFeeds() {
        ArrayList<Feed> feeds = new ArrayList<>();
        try {
            String res = HttpHelper.GET(AppConstants.Cloud.FEED_URL, true);
            if (isValidJsonArray(res)) {
                JSONArray arr = new JSONArray(res);
                for (int i = 0; i < arr.length(); i++) {
                    feeds.add(Feed.fromJSON(arr.getJSONObject(i)));
                }
            }
        } catch (IOException e) {  
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feeds;
    }

    public static boolean isValidJson(String json) {
        try {
            JSONObject o = new JSONObject(json);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isValidJsonArray(String json) {
        try {
            JSONArray o = new JSONArray(json);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
