package com.example.findmy.network;

import com.example.findmy.model.MarketListing;
import com.example.findmy.model.POI;
import com.example.findmy.model.POIRequest;
import com.example.findmy.model.Review;
import com.example.findmy.model.ReviewRequest;
import com.example.findmy.model.Transaction;
import com.example.findmy.model.User;
import com.example.findmy.model.UserRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NodeApiService {
    // User
    @GET("/users")
    Call<User[]> getUsers();

    @GET("/user/{id}")
    Call<User> getUser(@Path("id") int id);
    
    @GET("/user/email/{email}")
    Call<User> getUserByEmail(@Path("email") String email);

    @GET("/rscore/{userId}")
    Call<Integer> getUserReliabilityScore(@Path("userId") int id);

    @POST("/user")
    Call<User> createUser(@Body UserRequest user);

    @PUT("/user/{id}")
    Call<User> updateUser(@Path("id") int id, @Body UserRequest user);

    @DELETE("/user/{id}")
    Call<User> deleteUser(@Path("id") int id);

    // POI
    @GET("/pois")
    Call<POI[]> getPOIs();

    @GET("/poi/{id}")
    Call<POI> getPOI(@Path("id") int id);

    @GET("/filteredPois/{longitude}/{latitude}/{poiType}/{distance}")
    Call<POI[]> getFilteredPOIs(@Path("longitude") double lon, @Path("latitude") double lat, @Path("poiType") String category, @Path("distance") int distance);

    @POST("/poi")
    Call<POI> createPOI(@Body POIRequest poi);

    @PUT("/poi/{id}")
    Call<POI> updatePOI(@Path("id") int id, @Body POIRequest poi);

    @DELETE("/poi/{id}")
    Call<POI> deletePOI(@Path("id") int id);

    // Reviews
    @GET("/reviews/{searchBy}/{id}")
    Call<Review[]> listReviews(@Path("searchBy") String search, @Path("id") int id);
    @GET("/review/{id}")
    Call<Review> getReview(@Path("id") int id);
    @POST("/review")
    Call<Review> createReview(@Body ReviewRequest review);
    @PUT("/review/{id}")
    Call<Review> updateReview(@Path("id") int id, @Body ReviewRequest review);
    @PUT("/review/{id}/rating")
    Call<Review> updateRating(@Path("id") int id, @Body int rating);
    @DELETE("/review/{id}")
    Call<Review> deleteReview(@Path("id") int id);

    // MarketListings
    @GET("/marketListings")
    Call<MarketListing[]> getListings();
    @GET("/marketListing/{listingId}")
    Call<MarketListing> getListing(@Path("listingId") int id);
    @GET("/marketListings/{userId}")
    Call<MarketListing[]> getUserListings(@Path("userId") int id);
    @POST("/marketListing")
    Call<MarketListing> createListing(@Body int price, int sellerId, int poiId);
    @PUT("/marketListing/{listingId}")
    Call<MarketListing> updateListing(@Path("listingId") int id, @Body MarketListing listing);
    @DELETE("/marketListing/{listingId}")
    Call<MarketListing> deleteListing(@Path("listingId") int id);

    // Transactions
    @GET("/transaction/{transactionId}")
    Call<Transaction> getTransaction(@Path("transactionId") int id);
    @GET("/transactions/buyer/{userId}")
    Call<Transaction[]> getUserTransactions(@Path("userId") int id);
    @GET("/transactions/listing/{listingId}")
    Call<Transaction[]> getListingTransactions(@Path("listingId") int id);
    @POST("/transaction")
    Call<Transaction> createTransaction(@Body int buyerId, int listingId);
    @PUT("/transaction/{transactionId}")
    Call<Transaction> updateTransaction(@Path("transactionId") int id, @Body Transaction transaction);
    @DELETE("/transaction/{transactionId}")
    Call<Transaction> deleteTransaction(@Path("transactionId") int id);
}
