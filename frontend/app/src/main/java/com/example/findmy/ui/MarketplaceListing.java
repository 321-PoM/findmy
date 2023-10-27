package com.example.findmy.ui;

import com.example.findmy.POI.POI;

public class MarketplaceListing {
    private int listingAmount;
    private POI poi;

    MarketplaceListing(int listingAmount, POI poi) {
        this.poi = new POI(poi);
        this.listingAmount = listingAmount;
    }

    String getListingName() {
        return poi.getName();
    }
}
