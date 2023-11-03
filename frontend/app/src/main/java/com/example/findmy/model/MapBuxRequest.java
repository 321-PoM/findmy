package com.example.findmy.model;

import com.google.gson.annotations.SerializedName;

public class MapBuxRequest {
    @SerializedName("polarity")
    boolean polarity;

    @SerializedName("amount")
    int amount;

    public MapBuxRequest(boolean polarity, int amount) {
        this.polarity = polarity;
        this.amount = amount;
    }
}
