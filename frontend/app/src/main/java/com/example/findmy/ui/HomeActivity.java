package com.example.findmy.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.findmy.R;
import com.example.findmy.databinding.ActivityHomeBinding;
import com.example.findmy.model.User;
import com.example.findmy.network.FindMyServiceViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends BaseActivity {

    private User currentUser;

    FragmentManager manager =  getSupportFragmentManager();

    public NavController navController;

    private AppBarConfiguration appBarConfiguration;

    public LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Boolean isFineLocationGranted = (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        if (!isFineLocationGranted) {
            signOut();
        }

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        Intent intentFromMain = getIntent();
        currentUser = (User) intentFromMain.getSerializableExtra("CURRENTUSER");

        // get account
        ActivityHomeBinding binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // set toolbar
        Toolbar mainToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mainToolbar);

        BottomNavigationView bottomNavView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_marketplace,
                R.id.navigation_map ,
                R.id.navigation_friends,
                R.id.navigation_profile
        ).build();

        NavHostFragment navHostFragment = (NavHostFragment) manager.findFragmentById(R.id.nav_host_fragment_activity_home);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavView, navController);

        FindMyServiceViewModel findMyServiceViewModel;
        findMyServiceViewModel = new ViewModelProvider(this).get(FindMyServiceViewModel.class);
        findMyServiceViewModel.initFindMyService();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    public int getCurrentUserId() { return currentUser.getId(); }

    public User getCachedCurrentUser() { return currentUser; }

    public void setCachedCurrentUser(User currentUser) { this.currentUser = currentUser; }

    @Override
    protected void onResume() {
        super.onResume();
        // check location perm
        Boolean isFineLocationGranted = (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        if (!isFineLocationGranted) {
            signOut();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Boolean isFineLocationGranted = (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        if (!isFineLocationGranted) {
            signOut();
        }
    }
}