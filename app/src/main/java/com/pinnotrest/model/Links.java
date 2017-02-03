package com.pinnotrest.model;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Jayanth on 02-02-2017.
 */

public class Links implements Serializable {
    private String self, html, download;

    public Links(String self, String html, String download) {
        this.self = self;
        this.html = html;
        this.download = download;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public static Links fromJSON(JSONObject obj) {
        try {
            String self = obj.getString("self");
            String html = obj.getString("html");
            String download = obj.getString("download");
            return new Links(self, html, download);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
