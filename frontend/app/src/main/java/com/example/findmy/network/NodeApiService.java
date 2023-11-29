package com.example.findmy.network;

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
import com.example.findmy.model.Transaction;
import com.example.findmy.model.User;
import com.example.findmy.model.UserRequest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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

    @PUT("/user/{id}/updateUserBux")
    Call<MapBuxResponse> updateUserBux(@Path("id") int id, @Body MapBuxRequest request);

    // POI
    @GET("/pois/{userId}")
    Call<POI[]> getPOIs(@Path("userId") int userId);

    @GET("/poi/{id}")
    Call<POI> getPOI(@Path("id") int id);

    @GET("/filteredPois/{longitude}/{latitude}/{poiType}/{distance}/{userId}")
    Call<POI[]> getFilteredPOIs(@Path("longitude") double lon, @Path("latitude") double lat, @Path("poiType") String category, @Path("distance") int distance, @Path("userId") int userId);

    /* There may be better ways to do this... */
    @Multipart
    @POST("/poi")
    Call<POI> createPOI(@Part("latitude") RequestBody lat,
                        @Part("longitude") RequestBody lon,
                        @Part("category") RequestBody cat,
                        @Part("status") RequestBody stat,
                        @Part("description") RequestBody desc,
                        @Part("ownerId") RequestBody ownerId,
                        @Part("rating") RequestBody rating,
                        @Part MultipartBody.Part image);

    @PUT("/poi/{id}/{userId}")
    Call<POI> updatePOI(@Path("id") int id, @Path("userId") int userId, @Body POIRequest poi);

    @PUT("/poi/{id}/report")
    Call<Void> reportPOI(@Path("id") int id);

    @PUT("/poi/transaction/{transactionId}")
    Call<POI> transferPOI(@Path("transactionId") int id);
    @DELETE("/poi/{id}")
    Call<POI> deletePOI(@Path("id") int id);

    @PUT("/poi/{id}/buy/{buyerId}")
    Call<Void> buyPoi(@Path("id") int poiId, @Path("buyerId") int buyerId);

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
    Call<MarketListing> createListing(@Body MarketListingRequest listingRequest);
    @PUT("/marketListing/{listingId}")
    Call<MarketListing> updateListing(@Path("listingId") int id, @Body MarketListing listing);
    @DELETE("/marketListing/{listingId}")
    Call<MarketListing> deleteListing(@Path("listingId") int id);

    @GET("/marketListing/poi/{poiId}")
    Call<MarketListing[]> getMarketListingsByPoi(@Path("poiId") int id);

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

    // Friendship
    @GET("/friends/{userId}")
    Call<User[]> getFriendships(@Path("userId") int userId);
    @GET("/friends/{userId}/received")
    Call<Friendship[]> getReceivedFriendshipRequests(@Path("userId") int userId);
    @GET("/friends/{userId}/sent")
    Call<Friendship[]> getSentFriendshipRequests(@Path("userId") int id);
    @GET("/friend/{friendshipId}")
    Call<Friendship> getFriendship(@Path("friendshipId") int friendshipId);
    @POST("/friend")
    Call<Friendship> createFriendship(@Body FriendshipRequest friendshipRequest);
    @PUT("/friend/{friendshipId}/{acceptRequest}")
    Call<Friendship> respondToFriendship(@Path("friendshipId") int friendshipId, @Path("acceptRequest") String acceptRequest);
}
