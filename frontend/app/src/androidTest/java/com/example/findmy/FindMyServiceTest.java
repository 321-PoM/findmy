package com.example.findmy;

import com.example.findmy.model.POI;
import com.example.findmy.model.User;
import com.example.findmy.model.UserRequest;
import com.example.findmy.network.FindMyService;
import com.example.findmy.network.FindMyServiceViewModel;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindMyServiceTest {
    static FindMyServiceViewModel findMyServiceViewModel;

    // Used to synchronize request thread
    CountDownLatch signal;

    public void printUser(User[] users) {
        if (users == null) {
            System.out.println("NULL response");
            return;
        }
        for (User user: users) {
            Gson gson = new Gson();
            String json = gson.toJson(user); //convert
            System.out.println("User: \n" + json);
        }
    }

    public void printPOI(POI[] pois) {
        if (pois == null) {
            System.out.println("NULL response");
            return;
        }
        for (POI poi: pois) {
            Gson gson = new Gson();
            String json = gson.toJson(poi); //convert
            System.out.println("POI: \n" + json);
        }
    }

    @BeforeClass
    public static void findmy() {
        findMyServiceViewModel = new FindMyServiceViewModel();

        findMyServiceViewModel.initFindMyService();
    }

    @Before
    public void initialize() {
        signal = new CountDownLatch(1);

    }

    // user
    @Test
    public void testCreateUser() throws InterruptedException {
        FindMyService findMyService = findMyServiceViewModel.getFindMyService();

        Call<User> call = findMyService.createUser(UserRequest.testUserRequest);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User[] res = {response.body()};
                printUser(res);

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

    @Test
    public void testGetUsers() throws InterruptedException {
        FindMyService findMyService = findMyServiceViewModel.getFindMyService();
        findMyService.getUsers().enqueue(new Callback<User[]>() {
            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                printUser(response.body());

                signal.countDown();
            }

            @Override
            public void onFailure(Call<User[]> call, Throwable t) {
                System.out.println("Failed");

                signal.countDown();
            }
        });
        // wait for response to call signal.countDown() before terminating test;
        signal.await();
    }

    @Test
    public void testGetUserByEmail() throws InterruptedException {
        FindMyService findMyService = findMyServiceViewModel.getFindMyService();

        findMyService.getUserByEmail("kk@example.com", true).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User[] res = {response.body()};
                printUser(res);

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

    @Test
    public void testGetUserById() throws InterruptedException {
        FindMyService findMyService = findMyServiceViewModel.getFindMyService();
        findMyService.getUser(1).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User[] res = {response.body()};
                printUser(res);

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

    @Test
    public void testGetUserReliabilityScore() throws InterruptedException {
        FindMyService findMyService = findMyServiceViewModel.getFindMyService();
        findMyService.getUserReliabilityScore(1).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                System.out.println("Response Body: " + response.body());

                signal.countDown();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                System.out.println("Failed");

                signal.countDown();
            }
        });
        // wait for response to call signal.countDown() before terminating test;
        signal.await();
    }

    @Test
    public void testUpdateUser() throws InterruptedException {
        FindMyService findMyService = findMyServiceViewModel.getFindMyService();
        findMyService.updateUser(2, UserRequest.testUserRequest).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User[] res = {response.body()};
                printUser(res);

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

    @Test
    public void testDeleteUser() throws InterruptedException {
        FindMyService findMyService = findMyServiceViewModel.getFindMyService();
        findMyService.deleteUser(2).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User[] res = {response.body()};
                printUser(res);

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

    //POI
    @Test
    public void testGetPOIs() throws InterruptedException {
        FindMyService findMyService = findMyServiceViewModel.getFindMyService();
        findMyService.getPOIs(1).enqueue(new Callback<POI[]>() {
            @Override
            public void onResponse(Call<POI[]> call, Response<POI[]> response) {
                printPOI(response.body());

                signal.countDown();
            }

            @Override
            public void onFailure(Call<POI[]> call, Throwable t) {
                System.out.println("Failed");

                signal.countDown();
            }
        });
        // wait for response to call signal.countDown() before terminating test;
        signal.await();
    }

    @Test
    public void testGetPOIById() throws InterruptedException {
        FindMyService findMyService = findMyServiceViewModel.getFindMyService();
        findMyService.getPOI(1).enqueue(new Callback<POI>() {
            @Override
            public void onResponse(Call<POI> call, Response<POI> response) {
                POI[] res = {response.body()};
                printPOI(res);

                signal.countDown();
            }

            @Override
            public void onFailure(Call<POI> call, Throwable t) {
                System.out.println("Failed");

                signal.countDown();
            }
        });
        // wait for response to call signal.countDown() before terminating test;
        signal.await();
    }




}