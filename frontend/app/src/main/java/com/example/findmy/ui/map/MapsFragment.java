package com.example.findmy.ui.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.findmy.model.POI;
import com.example.findmy.R;
import com.example.findmy.network.FindMyService;
import com.example.findmy.network.FindMyServiceViewModel;
import com.example.findmy.ui.HomeActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsFragment extends Fragment implements LocationListener, AdapterView.OnItemSelectedListener, GoogleMap.OnMarkerClickListener {

    private final String TAG = "Map";
    private LocationManager locationManager;

    private FloatingActionButton newPOIButton;

    private FindMyService findMyService;

    GoogleMap mMap = null;

    Spinner filterSpinner;

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            try {
                // customize map
                boolean success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                MapsFragment.this.requireContext(), R.raw.gmap_style
                        )
                );
            } catch (Resources.NotFoundException e) {
                Log.e(TAG, "Google Map style parsing failed");
            }
            mMap = googleMap;
            mMap.setOnMarkerClickListener(MapsFragment.this);
            updateMapToUser(googleMap);

            String spinnerSelection = filterSpinner.getSelectedItem().toString();
            updateMapPins(spinnerSelection);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        findMyService = new ViewModelProvider(requireActivity()).get(FindMyServiceViewModel.class).getFindMyService();
        getLocationPermissions();
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        // setup button
        newPOIButton = view.findViewById(R.id.newPOIButton);
        newPOIButton.setOnClickListener( new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     HomeActivity homeActivity = (HomeActivity) requireActivity();
                     homeActivity.navController.navigate(R.id.action_navigation_map_to_navigation_add_poi);
                 }
             }
        );

        // setup spinner
        filterSpinner= view.findViewById(R.id.spinner_filter);
        // TODO: Change spinner layout here
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.POI_filter_choices,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        filterSpinner.setSelection(0);
        filterSpinner.setAdapter(adapter);
        filterSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.d(TAG, "Lat: " + location.getLatitude() + " | Long: " + location.getLongitude());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        updateMapPins((String) parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        updateMapPins("All");
    }
    private boolean checkLocationPermissions() {
        Boolean isFineLocationGranted = (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        Boolean isCoarseLocationGranted = (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        // TODO: Implement coarse
        return isFineLocationGranted;
    }

    @SuppressLint("MissingPermission")
    private void updateMapToUser(GoogleMap googleMap) {
        if (checkLocationPermissions()) {
            googleMap.setMyLocationEnabled(true);
            Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                LatLng currentLocation= new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                googleMap.moveCamera(CameraUpdateFactory.zoomTo((float)15.0));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
            }
            // update to currentLocation if possible
            updateMapToCurrentLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocationPermissions() {
        FragmentActivity parentActivity = requireActivity();
        locationManager = (LocationManager) parentActivity.getSystemService(Context.LOCATION_SERVICE);
        if (!checkLocationPermissions()) {
            // get permissions
            String[] permissionsRequested = {
//                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            };
            ActivityCompat.requestPermissions(parentActivity, permissionsRequested, 1);
        } else {
            // TODO: need to call this somewhere again, if permissions had to be requested
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
        }
    }

    @SuppressLint("MissingPermission")
    private void updateMapToCurrentLocation() {
        // TODO, should use getCurrentLocation or https://stackoverflow.com/questions/64853673/how-to-use-locationmanagergetcurrentlocation-in-android
    }

    private void clearMapPins() {
        mMap.clear();
    }

    private void updateMapPins(String category) {
        // TODO: Complete with backend
        if (mMap == null) { return; }
        clearMapPins();
        // TODO - lat, lon, and distance, are stubs, please extract it from current location
        double lon = 10;
        double lat = 10;
        int distance = 10;
        Call<POI[]> callFilteredPOIs = findMyService.getFilteredPOIs(lon, lat, category, distance);

        callFilteredPOIs.enqueue(new Callback<POI[]>() {
            @Override
            public void onResponse(Call<POI[]> call, Response<POI[]> response) {
                POI[] filteredPOIs = response.body();
                for (POI poi : filteredPOIs) {
                    placePOI(mMap, poi);
                }
            }

            @Override
            public void onFailure(Call<POI[]> call, Throwable t) {
                //TODO - do something
            }
        });


    }

    private void placePOI(GoogleMap gMap, POI poi) {
        LatLng loc = new LatLng(poi.getLatitude(), poi.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions()
                .position(loc)
                .title(poi.getName());
        Marker marker = gMap.addMarker(markerOptions);
        // may need to clear tag upon clearing pin
        marker.setTag(poi);
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        // TODO: testPOI
        MapPOIBottomSheet mapPoiBottomSheet = new MapPOIBottomSheet(POI.testPOI);

        mapPoiBottomSheet.show(requireActivity().getSupportFragmentManager(), TAG);
        return true;
    }
}