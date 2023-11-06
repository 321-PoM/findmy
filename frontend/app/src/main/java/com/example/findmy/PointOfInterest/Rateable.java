package com.example.findmy.PointOfInterest;

import java.util.Date;

public interface Rateable {

    double getRating(RatingType type) throws UnsupportedOperationException;
    void updateRating(RatingType type, double newRating, Date timeOfRating) throws UnsupportedOperationException;

    double resetRating(RatingType ratingType) throws UnsupportedOperationException;
}
