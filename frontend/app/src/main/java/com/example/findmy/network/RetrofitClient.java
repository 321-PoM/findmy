package com.example.findmy.network;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit nodeRetrofit;
    private static final String NODE_URL = "http://3.232.242.38:8081/";
    public static Retrofit getNodeRetrofitInstance() {
        if (nodeRetrofit == null) {
            nodeRetrofit = new Retrofit.Builder()
                    .baseUrl(NODE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return nodeRetrofit;
    }
}
