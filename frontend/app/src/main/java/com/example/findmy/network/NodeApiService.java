package com.example.findmy.network;

import com.example.findmy.model.POI;
import com.example.findmy.model.User;

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

    @GET("/rscore/{userId}")
    Call<Integer> getUserReliabilityScore(@Path("userId") int id);

    @POST("/user")
    Call<User> createUser(@Body User user);

    @PUT("/user/{id}")
    Call<User> updateUser(@Path("id") int id, @Body User user);

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
    Call<POI> createPOI(@Body POI poi);

    @PUT("/poi/{id}")
    Call<POI> updatePOI(@Path("id") int id, @Body POI poi);

    @DELETE("/poi/{id}")
    Call<POI> deletePOI(@Path("id") int id);
}
