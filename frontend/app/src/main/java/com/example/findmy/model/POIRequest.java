package com.example.findmy.model;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class POIRequest {
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

    @SerializedName("imageUrl")
    private File image;

    //public static final POIRequest testPOI = new POIRequest(0.0, 0.0, "bathroom", "test status", "Da Bathroom", 0, 3, 3, false, new Bitmap());

    public POIRequest(double latitude, double longitude, String category, String status, String description, int ownerId, int rating, File image) {
       this.latitude = latitude;
       this.longitude = longitude;
       this.category = category;
       this.status = status;
       this.description = description;
       this.ownderId = ownerId;
       this.rating = rating;
       this.image = image;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
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

}
