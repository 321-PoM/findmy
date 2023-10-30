package com.example.findmy.ui;

import com.example.findmy.POI.POI;
import com.example.findmy.user.User;

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

    POI getPOI() {
        return poi;
    }

    int getListingAmount() {
        return this.listingAmount;
    }

    User getOwner() {
        return poi.getOwner();
    }
}
