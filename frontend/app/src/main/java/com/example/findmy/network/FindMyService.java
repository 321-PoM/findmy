package com.example.findmy.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.findmy.model.Friendship;
import com.example.findmy.model.FriendshipRequest;
import com.example.findmy.model.MapBuxRequest;
import com.example.findmy.model.MapBuxResponse;
import com.example.findmy.model.MarketListing;
import com.example.findmy.model.MarketListingRequest;
import com.example.findmy.model.POI;
import com.example.findmy.model.POIRequest;
import com.example.findmy.model.Review;
import com.example.findmy.model.ReviewRequest;
import com.example.findmy.model.User;
import com.example.findmy.model.UserRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindMyService implements Serializable {

    private final NodeApiService apiService;

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
    public Call<User> getUserByEmail(String email, Boolean shouldCreate) {
        String create = shouldCreate? "true" : "false";
        return apiService.getUserByEmail(email, create);
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

    public Call<MapBuxResponse> updateUserMapBux(int id, MapBuxRequest request) { return apiService.updateUserBux(id, request); }

    // poi
    public Call<POI[]> getPOIs(int userId) {
        return apiService.getPOIs(userId);
    }
    public Call<POI> getPOI(int id) {
        return apiService.getPOI(id);
    }
    public Call<POI[]> getFilteredPOIs(double lon, double lat, String category, int distance, int userId) {
        return apiService.getFilteredPOIs(lon, lat, category, distance, userId);
    }
    public Call<POI> createPOI(POIRequest poi){

        // Image URL here is the path at which the user refers to the image to upload
        RequestBody requestFile = RequestBody.create(poi.getImage(), MediaType.parse("image/*"));
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", poi.getImage().getName(), requestFile);

        RequestBody lat = RequestBody.create(String.valueOf(poi.getLatitude()), MediaType.parse("text/plain"));
        RequestBody lon = RequestBody.create(String.valueOf(poi.getLongitude()), MediaType.parse("text/plain"));
        RequestBody cat = RequestBody.create(poi.getCategory(), MediaType.parse("text/plain"));
        RequestBody stat = RequestBody.create(poi.getStatus(), MediaType.parse("text/plain"));
        RequestBody desc = RequestBody.create(poi.getDescription(), MediaType.parse("text/plain"));
        RequestBody ownerId = RequestBody.create(String.valueOf(poi.getOwnderId()), MediaType.parse("text/plain"));
        RequestBody rating = RequestBody.create(String.valueOf(poi.getRating()), MediaType.parse("text/plain"));

        return apiService.createPOI(lat, lon, cat, stat, desc, ownerId, rating, image);
    }
//    public Call<POI> updatePOI(int id, int userId, POIRequest poi) { return apiService.updatePOI(id, userId, poi); }
    public Call<Void> reportPOI(int id) { return apiService.reportPOI(id); }
//    public Call<POI> transferPOI(int transactionId) { return apiService.transferPOI(transactionId); }
    public Call<POI> deletePOI(int id) { return apiService.deletePOI(id); }
    public Call<Void> buyPoi(int poiId, int buyerId) { return apiService.buyPoi(poiId, buyerId); }

    // Review
//    public Call<Review[]> listReviews(String searchBy, int id) { return apiService.listReviews(searchBy, id); }
//    public Call<Review> getReview(int id) { return apiService.getReview(id); }
    public Call<Review> createReview(ReviewRequest review) { return apiService.createReview(review); }
//    public Call<Review> updateReview(int id, ReviewRequest review) { return apiService.updateReview(id, review); }
//    public Call<Review> updateRating(int id, int rating) { return apiService.updateRating(id, rating); }
//    public Call<Review> deleteReview(int id) { return apiService.deleteReview(id); }

    // MarketListings
    public Call<MarketListing[]> getListings() { return apiService.getListings(); }
//    public Call<MarketListing> getListing(int id) { return apiService.getListing(id); }
//    public Call<MarketListing[]> getUserListings(int userId) { return apiService.getUserListings(userId); }
    public Call<MarketListing> createListing(MarketListingRequest marketListingRequest) { return apiService.createListing(marketListingRequest); }
//    public Call<MarketListing> updateListing(MarketListing listing) { return apiService.updateListing(listing.getId(), listing); }
    public Call<MarketListing> deleteListing(int id) { return apiService.deleteListing(id); }

    public Call<MarketListing[]> getMarketListingsByPoi(int id) { return apiService.getMarketListingsByPoi(id); }

    // Transactions
//    public Call<Transaction> getTransaction(int id) { return apiService.getTransaction(id); }
//    public Call<Transaction[]> getUserTransactions(int userId) { return apiService.getUserTransactions(userId); }
//    public Call<Transaction[]> getListingTransactions(int listingId) { return apiService.getListingTransactions(listingId); }
//    public Call<Transaction> createTransaction(int buyerId, int listingId) { return apiService.createTransaction(buyerId, listingId); }
//    public Call<Transaction> updateTransaction(Transaction transaction) { return apiService.updateTransaction(transaction.getTransactionId(), transaction); }
//    public Call<Transaction> deleteTransaction(int id) { return apiService.deleteTransaction(id); }

    // Friendship

    public Call<User[]> getFriendships(int userId) {
        return apiService.getFriendships(userId);
    }
//    public Call<User[]> getReceivedFriendshipRequests(int userId) {
//        return apiService.getReceivedFriendshipRequests(userId);
//    }
    public Call<User[]> getSentFriendshipRequests(int userId) {
        return apiService.getSentFriendshipRequests(userId);
    }
//    public Call<Friendship> getFriendship(int friendshipId) {
//        return apiService.getFriendship(friendshipId);
//    }
    public Call<Friendship> createFriendship(FriendshipRequest friendshipRequest) {
        return apiService.createFriendship(friendshipRequest);
    }

    public Call<Friendship> respondToFriendship(int friendshipId, boolean acceptRequest) {
        String accept = acceptRequest? "true" : "false";
        return apiService.respondToFriendship(friendshipId, accept);
    }
    public void showErrorToast(Context context) {
        Toast.makeText(context, "Failed - Try again later", Toast.LENGTH_LONG).show();
    }

    public String getErrorMessage(Response response) {
        ResponseBody errBody = response.errorBody();
        JSONObject errObj = null;
        String msg;
        try {
            errObj = new JSONObject(errBody.string());
            msg = errObj.getString("message");
        } catch (JSONException | IOException e) {
            Log.d("TEST", e.toString());
            return e.toString();
        }
        return msg;
    }

    public void getCurrentUser(int id, Callback<User> onReadyCallback) {
        getUser(id).enqueue(onReadyCallback);
    }
}