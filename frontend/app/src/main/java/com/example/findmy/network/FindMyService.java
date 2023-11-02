package com.example.findmy.network;

import com.example.findmy.model.Friendship;
import com.example.findmy.model.FriendshipRequest;
import com.example.findmy.model.MarketListing;
import com.example.findmy.model.POI;
import com.example.findmy.model.POIRequest;
import com.example.findmy.model.Review;
import com.example.findmy.model.ReviewRequest;
import com.example.findmy.model.Transaction;
import com.example.findmy.model.User;
import com.example.findmy.model.UserRequest;

import java.io.Serializable;

import retrofit2.Call;

public class FindMyService implements Serializable {

    private NodeApiService apiService;

    public FindMyService() {
        apiService = RetrofitClient.getNodeRetrofitInstance().create(NodeApiService.class);
    }

    // user
    public Call<User[]> getUsers() {
        return apiService.getUsers();
    }
    public Call<User> getUser(int id) {
        return apiService.getUser(id);
    }
    public Call<User> getUserByEmail(String email) {
        return apiService.getUserByEmail(email);
    }
    public Call<Integer> getUserReliabilityScore(int id) {
        return apiService.getUserReliabilityScore(id);
    }
    public Call<User> createUser(UserRequest userRequest) {
        return apiService.createUser(userRequest);
    }
    public Call<User> updateUser(int id, UserRequest userRequest) {
        return apiService.updateUser(id, userRequest);
    }
    public Call<User> deleteUser(int id) {
        return apiService.deleteUser(id);
    }

    // poi
    public Call<POI[]> getPOIs() {
        return apiService.getPOIs();
    }
    public Call<POI> getPOI(int id) {
        return apiService.getPOI(id);
    }
    public Call<POI[]> getFilteredPOIs(double lon, double lat, String category, int distance) {
        return apiService.getFilteredPOIs(lon, lat, category, distance);
    }
    public Call<POI> createPOI(POIRequest poi){ return apiService.createPOI(poi); }
    public Call<POI> updatePOI(int id, POIRequest poi) { return apiService.updatePOI(id, poi); }
    public Call<POI> reportPOI(int id) { return apiService.reportPOI(id); }
    public Call<POI> transferPOI(int transactionId) { return apiService.transferPOI(transactionId); };
    public Call<POI> deletePOI(int id) { return apiService.deletePOI(id); }

    // Review
    public Call<Review[]> listReviews(String searchBy, int id) { return apiService.listReviews(searchBy, id); }
    public Call<Review> getReview(int id) { return apiService.getReview(id); }
    public Call<Review> createReview(ReviewRequest review) { return apiService.createReview(review); }
    public Call<Review> updateReview(int id, ReviewRequest review) { return apiService.updateReview(id, review); }
    public Call<Review> updateRating(int id, int rating) { return apiService.updateRating(id, rating); }
    public Call<Review> deleteReview(int id) { return apiService.deleteReview(id); }

    // MarketListings
    public Call<MarketListing[]> getListings() { return apiService.getListings(); }
    public Call<MarketListing> getListing(int id) { return apiService.getListing(id); }
    public Call<MarketListing[]> getUserListings(int userId) { return apiService.getUserListings(userId); }
    public Call<MarketListing> createListing(int price, int sellerId, int poiId) { return apiService.createListing(price, sellerId, poiId); }
    public Call<MarketListing> updateListing(MarketListing listing) { return apiService.updateListing(listing.getId(), listing); }
    public Call<MarketListing> deleteListing(int id) { return apiService.deleteListing(id); }

    // Transactions
    public Call<Transaction> getTransaction(int id) { return apiService.getTransaction(id); }
    public Call<Transaction[]> getUserTransactions(int userId) { return apiService.getUserTransactions(userId); }
    public Call<Transaction[]> getListingTransactions(int listingId) { return apiService.getListingTransactions(listingId); }
    public Call<Transaction> createTransaction(int buyerId, int listingId) { return apiService.createTransaction(buyerId, listingId); }
    public Call<Transaction> updateTransaction(Transaction transaction) { return apiService.updateTransaction(transaction.getTransactionId(), transaction); }
    public Call<Transaction> deleteTransaction(int id) { return apiService.deleteTransaction(id); }

    // Friendship

    public Call<Friendship[]> getFriendships(int userId) {
        return apiService.getFriendships(userId);
    }
    public Call<Friendship[]> getReceivedFriendshipRequests(int userId) {
        return apiService.getReceivedFriendshipRequests(userId);
    }
    public Call<Friendship[]> getSentFriendshipRequests(int userId) {
        return apiService.getSentFriendshipRequests(userId);
    }
    public Call<Friendship> getFriendship(int friendshipId) {
        return apiService.getFriendship(friendshipId);
    }
    public Call<Friendship> createFriendship(FriendshipRequest friendshipRequest) {
        return apiService.createFriendship(friendshipRequest);
    }

    public Call<Friendship> respondToFriendship(int friendshipId, boolean acceptRequest) {
        String accept = acceptRequest? "true" : "false";
        return apiService.respondToFriendship(friendshipId, accept);
    }
}