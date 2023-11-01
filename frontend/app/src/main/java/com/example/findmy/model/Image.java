package com.example.findmy.model;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("id")
    private int id;

    @SerializedName("url")
    private String url;

    @SerializedName("poiId")
    private int poiId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPoiId() {
        return poiId;
    }

    public void setPoiId(int poiId) {
        this.poiId = poiId;
    }
}
