package com.example.findmy.ui;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class LocationFragment extends Fragment implements LocationListener {

    protected Location currentLocation;
    protected LocationManager locationManager;
    protected Location lastLocation;


    protected void setupLocationManager(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        currentLocation = location;
    }

    protected boolean checkLocationPermissions() {
        Boolean isFineLocationGranted = (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        return isFineLocationGranted;
    }

    @SuppressLint("MissingPermission")
    protected void setupCurrentLocation() {
        currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    protected void abortToMainActivity() {
        ((HomeActivity) requireActivity()).signOut();
    }

    @SuppressLint("MissingPermission")
    protected void getLocationPermissions() {
        if (!checkLocationPermissions()) {
            ((HomeActivity) requireActivity()).signOut();
            return;
        } else {
            // TODO: need to call this somewhere again, if permissions had to be requested
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
            if(!checkLocationPermissions()) {
                // still not granted goto MainActivity
                abortToMainActivity();
            }
        }

        setupCurrentLocation();
    }

    @SuppressLint("MissingPermission")
    protected Location getLastLocation() {
        if (!checkLocationPermissions()) {
            abortToMainActivity();
        }
        lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return lastLocation;
    }

    @Override
    public void onResume() {
        super.onResume();
        getLocationPermissions();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupLocationManager(((HomeActivity) requireActivity()).locationManager);
        if (savedInstanceState != null) {
            getLocationPermissions();
        }
    }
}
