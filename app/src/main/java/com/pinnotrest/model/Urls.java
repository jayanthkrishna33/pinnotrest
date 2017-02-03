package com.pinnotrest.model;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Jayanth on 02-02-2017.
 */

public class Urls implements Serializable {
    private String raw, full, regular, small, thumb;

    public Urls(String raw, String full, String regular, String small, String thumb) {
        this.raw = raw;
        this.full = full;
        this.regular = regular;
        this.small = small;
        this.thumb = thumb;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public static Urls fromJSON(JSONObject o) {
        try {
            String raw = o.getString("raw");
            String full = o.getString("full");
            String regular = o.getString("regular");
            String small = o.getString("small");
            String thumb = o.getString("thumb");
            return new Urls(raw, full, regular, small, thumb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
