package com.example.findmy.POI;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class POI implements Rateable {

    private Map<RatingType, Double> ratings;

    private Date lastTimeRetrievedUpdate;

    POIType poiType;

    private double longitude;
    private double latitude;

    String name;

    public enum POIType {
      washroom,
      studySpace,
      microwave,
      myPOI
    }

    public static POI testPOI = new POI(POI.POIType.washroom, new RatingType[]{RatingType.cleanliness}, -123.128, 49.1785, "Da Bathroom");

    public POI (POIType poiType, RatingType[] supportedRatingTypes, double longitude, double latitude, String name) {
        this.poiType = poiType;

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
        this.poiType = other.poiType;
        this.ratings = new HashMap<>(other.ratings);

        this.longitude = other.longitude;
        this.latitude = other.latitude;

        this.name = other.name;
        this.lastTimeRetrievedUpdate = other.lastTimeRetrievedUpdate;
    }

    public static List<POI> retrievePOIs() {
        ArrayList<POI> list = new ArrayList<>();

        // TODO: change to call to backend api
        list.add(POI.testPOI);
        return list;
    }

    public static List<POI> retrievePOIsWithType(POIType type) {

        return new ArrayList<>();
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

    public double getLatitude() { return this.latitude; }

    public double getLongitude() { return this.longitude; }

    public boolean submitNewToBackend() {
        return false;
    }

    public String getName() {
        return this.name;
    }
}
