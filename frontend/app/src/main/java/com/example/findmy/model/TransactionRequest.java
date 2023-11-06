package com.example.findmy.model;

import com.google.gson.annotations.SerializedName;

public class TransactionRequest {
    @SerializedName("buyer")
    private User buyer;

    @SerializedName("buyerId")
    private int buyerId;
    @SerializedName("listingId")
    private int listingId;

    public TransactionRequest(User buyer, int buyerId, int listingId) {
        this.buyer = buyer;
        this.buyerId = buyerId;
        this.listingId = listingId;
    }
    public int getBuyerId() { return buyerId; }
    public void setBuyerId(int buyerId) { this.buyerId = buyerId; }
    public int getListingId() { return listingId; }
    public void setListingId(int listingId) { this.listingId = listingId; }

    public User getBuyer() { return this.buyer; }
}
