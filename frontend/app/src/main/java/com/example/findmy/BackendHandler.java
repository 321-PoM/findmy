package com.example.findmy;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BackendHandler {

    private final static String TAG = "BackendHandler";
    static String url = "https://findastar.westus2.cloudapp.azure.com";

    OkHttpClient client = new OkHttpClient();

    public enum HttpMethod {
        POST,
        GET,
        PUT,
        DELETE
    }

    private static final MediaType JSON = MediaType.parse("application/json, charset=utf-8");

    public BackendHandler() {}

    public void makeRequest(Callback callback, String resource, HttpMethod method, JSONObject body) {
        Request.Builder builder = new Request.Builder()
                .url(BackendHandler.url + resource);

        RequestBody rBody = RequestBody.create(body.toString(), JSON);

        switch (method) {
            case POST:
                builder = builder.post(rBody);
                break;
            case GET:
                builder = builder.get();
                break;
            case PUT:
                builder = builder.put(rBody);
                break;
            case DELETE:
                builder = builder.delete();
                break;
            default:
                // not possible since this encompasses all enum states
                throw new IllegalStateException("Unexpected value: " + method);
        }

        Request request = builder.build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                try {
                    Log.d(TAG, e.toString());
                    callback.onFailure(call,e );
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    Log.d(TAG, response.toString());
                    callback.onResponse(call, response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
