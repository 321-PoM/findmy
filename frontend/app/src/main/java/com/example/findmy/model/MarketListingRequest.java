package com.example.findmy.model;

import com.google.gson.annotations.SerializedName;

public class MarketListingRequest {
    @SerializedName("price")
    private float price;

    @SerializedName("sellerId")
    private int sellerId;

    @SerializedName("poiId")
    int poiID;

    @SerializedName("isActive")
    private boolean isActive;

    @SerializedName("isDeleted")
    private boolean isDeleted;

    public MarketListingRequest(float price, int sellerId, int poiID, boolean isActive, boolean isDeleted) {
        this.price = price;
        this.sellerId = sellerId;
        this.poiID = poiID;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
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
