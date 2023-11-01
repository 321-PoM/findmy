package com.example.findmy.model;

import com.example.findmy.DateWrapper;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class User {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("biography")
    private String biography;

    @SerializedName("reliabilityScore")
    private int reliabilityScore;

    @SerializedName("premiumStatus")
    private boolean premiumStatus;

    @SerializedName("mapBux")
    private int mapBux;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    @SerializedName("isActive")
    private boolean isActive;

    @SerializedName("isDeleted")
    private boolean isDeleted;

    @SerializedName("poi")
    private POI[] poi;

    @SerializedName("Review")
    private Review[] reviews;

    @SerializedName("marketListing")
    private MarketListing[] marketListings;

    @SerializedName("Transaction")
    private Transaction[] transactions;

    public User(int id, String name, String email, String avatar, String biography, int reliabilityScore, boolean premiumStatus, int mapBux, String createdAt, String updatedAt, boolean isActive, boolean isDeleted, POI[] poi, Review[] reviews, MarketListing[] marketListings, Transaction[] transactions) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mapBux = mapBux;
        this.avatar = avatar;
        this.biography = biography;
        this.reliabilityScore = reliabilityScore;
        this.premiumStatus = premiumStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
        this.poi = poi;
        this.reviews = reviews;
        this.marketListings = marketListings;
        this.transactions = transactions;
    }

    public static final User testUser = new User(
            0,
        "test_name", "test@test.com", "noavatar", "my bio", 3, true, 10, DateWrapper.testDate.getISOString(), DateWrapper.testDate.getISOString(), true, false, new POI[]{}, new Review[]{}, new MarketListing[]{}, new Transaction[]{}
    );

    public String getName() {
        return this.name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public int getReliabilityScore() {
        return reliabilityScore;
    }

    public void setReliabilityScore(int reliabilityScore) {
        this.reliabilityScore = reliabilityScore;
    }

    public boolean isPremiumStatus() {
        return premiumStatus;
    }

    public void setPremiumStatus(boolean premiumStatus) {
        this.premiumStatus = premiumStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}