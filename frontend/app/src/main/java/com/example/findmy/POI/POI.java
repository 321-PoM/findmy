package com.example.findmy.POI;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class POI implements Rateable {

    private Map<RatingType, Double> ratings;

    private Date lastTimeRetrievedUpdate;

    POI (Collection<RatingType> supportedRatingTypes) {
        for (RatingType type : supportedRatingTypes) {
            ratings.put(type, -1.0);
        }
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
}
