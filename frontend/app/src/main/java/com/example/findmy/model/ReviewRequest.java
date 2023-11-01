package com.example.findmy.model;

import com.google.gson.annotations.SerializedName;

public class ReviewRequest {
    @SerializedName("userId")
    private int userId;

    @SerializedName("poiId")
    private int poiId;

    @SerializedName("rating")
    private int rating;

    @SerializedName("description")
    private String description;

    @SerializedName("reliabilityScore")
    private int reliabilityScore;

    @SerializedName("isDeleted")
    private boolean isDeleted;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPoiId() {
        return poiId;
    }

    public void setPoiId(int poiId) {
        this.poiId = poiId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReliabilityScore() {
        return reliabilityScore;
    }

    public void setReliabilityScore(int reliabilityScore) {
        this.reliabilityScore = reliabilityScore;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
