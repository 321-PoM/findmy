package com.example.findmy.model;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.Comparator;

public class POIComparator implements Comparator<POI>{

    private LatLng currentLatLng;

    public POIComparator(LatLng currentLatLng) { this.currentLatLng = currentLatLng; }

    @Override
    public int compare(POI lhs, POI rhs) {
        float[] results = new float[1];
        Location.distanceBetween(currentLatLng.latitude, currentLatLng.longitude, lhs.getLatitude(), lhs.getLongitude(), results);
        double distanceLhs = results[0];
        Location.distanceBetween(currentLatLng.latitude, currentLatLng.longitude, rhs.getLatitude(), rhs.getLongitude(), results);
        double distanceRhs = results[0];
        return (distanceLhs > distanceRhs) ? 1 : -1;
    }
}
