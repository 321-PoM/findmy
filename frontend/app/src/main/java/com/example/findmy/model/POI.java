package com.example.findmy.model;

import com.google.gson.annotations.SerializedName;

public class POI {
    @SerializedName("id")
    private int id;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("category")
    private String category;

    @SerializedName("status")
    private String status;

    @SerializedName("description")
    private String description;

    @SerializedName("ownerId")
    private int ownderId;

    @SerializedName("rating")
    private int rating;

    @SerializedName("reports")
    private int reports;

    @SerializedName("isDeleted")
    private boolean isDeleted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOwnderId() {
        return ownderId;
    }

    public void setOwnderId(int ownderId) {
        this.ownderId = ownderId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getReports() {
        return reports;
    }

    public void setReports(int reports) {
        this.reports = reports;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
