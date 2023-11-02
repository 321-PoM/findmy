package com.example.findmy.model;

import com.google.gson.annotations.SerializedName;

public class Friendship {
    @SerializedName("friendshipId")
    private int friendshipId;

    @SerializedName("userIdFrom")
    private int userIdFrom;

    @SerializedName("userIdTo")
    private int userIdTo;

    // Friendship status enums include {"requested", "accepted", "rejected"}
    @SerializedName("status")
    private String status;

    @SerializedName("createdAt")
    private String createdAt;

    public Friendship(int friendshipId, int userIdFrom, int userIdTo, String status, String createdAt) {
        this.friendshipId = friendshipId;
        this.userIdFrom = userIdFrom;
        this.userIdTo = userIdTo;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(int friendshipId) {
        this.friendshipId = friendshipId;
    }

    public int getUserIdFrom() {
        return userIdFrom;
    }

    public void setUserIdFrom(int userIdFrom) {
        this.userIdFrom = userIdFrom;
    }

    public int getUserIdTo() {
        return userIdTo;
    }

    public void setUserIdTo(int userIdTo) {
        this.userIdTo = userIdTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
