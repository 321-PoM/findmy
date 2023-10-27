package com.example.findmy;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class BackendHandler {
    static String url;

    OkHttpClient client = new OkHttpClient();

    public void makeRequest(Runnable onSuccess, Runnable onFailure) {
        Request request = new Request.Builder()
                .header("test", "test")
                .url(BackendHandler.url)
                .build();
    }
}
