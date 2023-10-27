package com.example.findmy.POI;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class POI implements Rateable {

    private Map<RatingType, Double> ratings;

    private Date lastTimeRetrievedUpdate;

    double longitude;
    double latitude;

    String name;

    public POI (RatingType[] supportedRatingTypes, double longitude, double latitude, String name) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;

        this.lastTimeRetrievedUpdate = new Date(0);

        this.ratings = new HashMap<>();
        for (RatingType type : supportedRatingTypes) {
            ratings.put(type, -1.0);
        }
    }

    public POI(POI other) {
        this.ratings = new HashMap<>(other.ratings);

        this.longitude = other.longitude;
        this.latitude = other.latitude;

        this.name = other.name;
    }

    @Override
    public double getRating(RatingType ratingType) throws UnsupportedOperationException {
        if (!ratings.containsKey(ratingType)) {
            throw new UnsupportedOperationException(String.format("Unsupported RatingType %s! Use only %s", ratingType, ratings.keySet()));
        }

        return ratings.get(ratingType);
    }

    @Override
    public void updateRating(RatingType ratingType, double newRating, Date timeOfRating) throws UnsupportedOperationException {
        if (!ratings.containsKey(ratingType)) {
            throw new UnsupportedOperationException(String.format("Unsupported RatingType %s! Use only %s", ratingType, ratings.keySet()));
        }
        // TODO: up
    }

    @Override
    public double resetRating(RatingType ratingType) throws UnsupportedOperationException {
        if (!ratings.containsKey(ratingType)) {
            throw new UnsupportedOperationException(String.format("Unsupported RatingType %s! Use only %s", ratingType, ratings.keySet()));
        }

        // TODO: update backend and retrieve new rating
        return 0.0;
    }

    public String getName() {
        return this.name;
    }
}
