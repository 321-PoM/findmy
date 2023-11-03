package com.example.findmy.ui;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.findmy.model.User;
import com.example.findmy.R;
import com.example.findmy.databinding.ActivityHomeBinding;
import com.example.findmy.network.FindMyServiceViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends BaseActivity {

    public User currentUser;

    private static final String TAG = "HomeActivity";

    private ActivityHomeBinding binding;
    FragmentManager manager =  getSupportFragmentManager();
    public NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private FindMyServiceViewModel findMyServiceViewModel;

    public LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        super.onCreate(savedInstanceState);

        Intent intentFromMain = getIntent();
        currentUser = (User) intentFromMain.getSerializableExtra("CURRENTUSER");

        // get account
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // set toolbar
        Toolbar mainToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mainToolbar);

        BottomNavigationView bottomNavView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home,
                R.id.navigation_marketplace,
                R.id.navigation_map ,
                R.id.navigation_friends,
                R.id.navigation_profile
        ).build();

        NavHostFragment navHostFragment = (NavHostFragment) manager.findFragmentById(R.id.nav_host_fragment_activity_home);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavView, navController);

        findMyServiceViewModel = new ViewModelProvider(this).get(FindMyServiceViewModel.class);
        findMyServiceViewModel.initFindMyService();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}