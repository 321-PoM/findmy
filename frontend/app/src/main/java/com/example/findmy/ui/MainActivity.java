package com.example.findmy.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.findmy.R;
import com.example.findmy.model.User;
import com.example.findmy.network.FindMyService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    final static String TAG="MainActivity";
    private static final int LOCATION_PERMISSION_REQUEST = 123;
    private Intent homeIntent;

    private static AtomicBoolean isRunningTest;

    public static synchronized boolean isRunningTest () {
        if (null == isRunningTest) {
            boolean istest;

            try {
                Class.forName ("androidx.test.espresso.Espresso");
                istest = true;
            } catch (ClassNotFoundException e) {
                istest = false;
            }

            isRunningTest = new AtomicBoolean (istest);
        }

        return isRunningTest.get();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // login button
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            attemptUpdateUI(account.getEmail());
        }
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
        } else {
            startActivity(homeIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);;
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            for(int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    showToast("This app requires location permissions");
                    return;
                }
            }
            startActivity(homeIntent);
        }
    }

    private void showToast(String text){
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(MainActivity.this, text, duration);
        toast.show();
    }

    protected void signIn() {
        boolean isTest = isRunningTest();

        if (isTest) {
            Log.d(TAG, "Automated Test Mode Enabled - Using dummy user account");
            attemptUpdateUI("test_user@gmail.com");
            return;
        }

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    protected void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            Log.d(TAG, "signInResult id: " + account.getId());

            // Signed in successfully, show authenticated UI.
            attemptUpdateUI(account.getEmail());
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());

            // goto main page
            Intent mainActivityIntent = new Intent(this, MainActivity.class);
            startActivity(mainActivityIntent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void attemptUpdateUI(String email) {

        FindMyService fmyService = new FindMyService();

        Call<User> currentUserCall = fmyService.getUserByEmail(email, true);

        currentUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Unable to login!", Toast.LENGTH_LONG).show();
                    return;
                }
                // goto HomeActivity
                Log.d(TAG, "Logged in!");

                homeIntent = new Intent(MainActivity.this, HomeActivity.class);

                User currentUser = response.body();
                homeIntent.putExtra("CURRENTUSER",currentUser);
                checkLocationPermission();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable to login!", Toast.LENGTH_LONG).show();
            }
        });
    }
}