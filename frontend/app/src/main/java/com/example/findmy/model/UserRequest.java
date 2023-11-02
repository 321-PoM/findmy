package com.example.findmy.model;

import com.example.findmy.DateWrapper;
import com.google.gson.annotations.SerializedName;

public class UserRequest {
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

    @SerializedName("isActive")
    private boolean isActive;

    @SerializedName("isDeleted")
    private boolean isDeleted;

    public UserRequest(int id, String name, String email, String avatar, String biography, int reliabilityScore, boolean premiumStatus, int mapBux, String createdAt, String updatedAt, boolean isActive, boolean isDeleted) {
        this.name = name;
        this.email = email;
        this.mapBux = mapBux;
        this.avatar = avatar;
        this.biography = biography;
        this.reliabilityScore = reliabilityScore;
        this.premiumStatus = premiumStatus;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
    }

    public static final UserRequest testUserRequest = new UserRequest(
            0,
        "test_name", "test@test.com", "noavatar", "my bio", 3, true, 10, DateWrapper.testDate.getISOString(), DateWrapper.testDate.getISOString(), true, false
    );

    public String getName() {
        return this.name;
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