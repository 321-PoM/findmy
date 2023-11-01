package com.example.findmy.model;

import com.google.gson.annotations.SerializedName;

public class Transaction {
    @SerializedName("id")
    private int id;

    @SerializedName("buyer")
    private User buyer;

    @SerializedName("buyerId")
    private int buyerId;

    @SerializedName("marketListing")
    private MarketListing listing;

    @SerializedName("listingId")
    private int listingId;

    public Transaction(int id, User buyer, int buyerId, MarketListing listing, int listingId) {
        this.id = id;
        this.buyer = buyer;
        this.buyerId = buyerId;
        this.listing = listing;
        this.listingId = listingId;
    }

    public int getTransactionId() { return id; }
    public void setTransactionId(int id) { this.id = id; }
    public int getBuyerId() { return buyerId; }
    public void setBuyerId(int buyerId) { this.buyerId = buyerId; }
    public int getListingId() { return listingId; }
    public void setListingId(int listingId) { this.listingId = listingId; }
}
