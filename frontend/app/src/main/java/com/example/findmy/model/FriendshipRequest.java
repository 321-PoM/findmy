package com.example.findmy.model;

import com.google.gson.annotations.SerializedName;

public class FriendshipRequest {
    @SerializedName("userIdFrom")
    private int userIdFrom;

    @SerializedName("userIdTo")
    private int userIdTo;


    public FriendshipRequest( int userIdFrom, int userIdTo, String status) {
        this.userIdFrom = userIdFrom;
        this.userIdTo = userIdTo;
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
}
