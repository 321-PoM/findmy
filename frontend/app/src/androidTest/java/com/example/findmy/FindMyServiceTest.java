package com.example.findmy;

import com.example.findmy.model.User;
import com.example.findmy.network.FindMyService;
import com.example.findmy.network.FindMyServiceViewModel;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindMyServiceTest {
    FindMyServiceViewModel findMyServiceViewModel;

    // Used to synchronize request thread
    CountDownLatch signal;

    @Before
    public void initialize() {
        signal = new CountDownLatch(1);
    }
    @Test
    public void testUser() throws InterruptedException {


        findMyServiceViewModel = new FindMyServiceViewModel();

        findMyServiceViewModel.initFindMyService();

        FindMyService findMyService = findMyServiceViewModel.getFindMyService();

        Call<User> call = findMyService.createUser(User.testUser);


        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                System.out.println("Response Body: " + response.body());

                signal.countDown();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("Failed");

                signal.countDown();
            }
        });

        // wait for response to call signal.countDown() before terminating test;
        signal.await();
    }
}
