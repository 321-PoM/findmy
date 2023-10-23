package com.example.findmy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class BaseActivity extends AppCompatActivity {
    protected GoogleSignInClient mGoogleSignInClient;
    protected final Integer RC_SIGN_IN = 1;

    private static final String TAG = "BaseActivity";

    protected void getSignInClient() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onStart() {
        super.onStart();

        getSignInClient();
    }

    public void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent broadcastIntent = new Intent();
                        broadcastIntent.setAction("com.example.findmy.ACTION_LOGOUT");
                        LocalBroadcastManager.getInstance(BaseActivity.this).sendBroadcast(broadcastIntent);
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.findmy.ACTION_LOGOUT");

        LocalBroadcastManager.getInstance(this).registerReceiver(
            new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG + " - onReceive", "Logout in progress");

                Intent loginIntent = new Intent(BaseActivity.this, MainActivity.class);
                startActivity(loginIntent);

                finish();
            }
        }, intentFilter);

        setContentView(R.layout.activity_base);
    }
}