package com.example.findmy.model;

import com.google.gson.annotations.SerializedName;

public class MarketListing {
    @SerializedName("id")
    private int id;

    @SerializedName("price")
    private float price;

    @SerializedName("seller")
    private User seller;

    @SerializedName("sellerId")
    private int sellerId;

    @SerializedName("poi")
    POI poi;

    @SerializedName("poiId")
    int poiID;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    @SerializedName("isActive")
    private boolean isActive;

    @SerializedName("isDeleted")
    private boolean isDeleted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public POI getPoi() {
        return this.poi;
    }

    public User getSeller() {
        return this.seller;
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
