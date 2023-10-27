package com.example.findmy;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BackendHandlerTest {
    BackendHandler handler;
    CountDownLatch signal;

    @Before
    public void initialize() {
        handler = new BackendHandler();
        signal = new CountDownLatch(1);
    }
    @Test
    public void testGetUser() throws InterruptedException, NullPointerException {
        JSONObject jsonObject = new JSONObject();
        Callback callback;
        callback = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                signal.countDown();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String body = response.body().string();

                assertNotNull(body);
                assertEquals(response.code(), 200);

                System.out.println("Response Body: " + body);

                signal.countDown();
            }
        };

        handler.makeRequest(callback, "/user", BackendHandler.HttpMethod.GET, jsonObject);

        signal.await();
    }
}
