package com.example.findmy.network;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit nodeRetrofit;

    private static final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static final OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
    private static final String NODE_URL = "https://findastar.westus2.cloudapp.azure.com/";
    public static Retrofit getNodeRetrofitInstance() {
        if (nodeRetrofit == null) {
            nodeRetrofit = new Retrofit.Builder()
                    .baseUrl(NODE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return nodeRetrofit;
    }
}
