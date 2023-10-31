package com.example.findmy.network;

import com.example.findmy.model.User;

import retrofit2.Call;

public class FindMyService {

    private NodeApiService apiService;

    public FindMyService() {
        apiService = RetrofitClient.getNodeRetrofitInstance().create(NodeApiService.class);
    }

    public Call<User[]> getUsers() {
        return apiService.getUsers();
    }

    public Call<User> getUser(int id) {
        return apiService.getUser(id);
    }

    public Call<Integer> getUserReliabilityScore(int id) {
        return apiService.getUserReliabilityScore(id);
    }

    public Call<User> createUser(User user) {
        return apiService.createUser(user);
    }

    public Call<User> updateUser(User user) {
        return apiService.updateUser(user.getId(), user);
    }

    public Call<User> deleteUser(int id) {
        return apiService.deleteUser(id);
    }

}