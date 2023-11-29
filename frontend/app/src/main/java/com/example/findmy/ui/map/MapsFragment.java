package com.example.findmy.ui.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
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
import com.example.findmy.model.User;
import com.example.findmy.network.FindMyService;
import com.example.findmy.network.FindMyServiceViewModel;
import com.example.findmy.ui.HomeActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsFragment extends Fragment implements LocationListener, AdapterView.OnItemSelectedListener, GoogleMap.OnMarkerClickListener {

    private final String TAG = "Map";

    private FindMyService findMyService;

    GoogleMap mMap = null;

    Spinner filterSpinner;

    Location currentLocation;

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
                googleMap.setMapStyle(
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

            refreshMapPins();
        }
    };
    private LocationManager locationManager;
    private User currentCachedUser;
    private int currentUserId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        findMyService = new ViewModelProvider(requireActivity()).get(FindMyServiceViewModel.class).getFindMyService();

        HomeActivity homeActivity = (HomeActivity) requireActivity();
        locationManager = homeActivity.locationManager;
        currentCachedUser = homeActivity.getCachedCurrentUser();
        currentUserId = homeActivity.getCurrentUserId();
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
        FloatingActionButton newPOIButton = view.findViewById(R.id.newPOIButton);
        newPOIButton.setOnClickListener( new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     AddPOIBottomSheet addPOIBottomSheet = new AddPOIBottomSheet(currentLocation);

                     addPOIBottomSheet.show(getChildFragmentManager(), TAG);
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
        currentLocation = location;
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
        if (!checkLocationPermissions()) {
            // get permissions
            String[] permissionsRequested = {
//                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            };
        } else {
            // TODO: need to call this somewhere again, if permissions had to be requested
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
            if(!checkLocationPermissions()) {
                // still not granted goto MainActivity
                ((HomeActivity) requireActivity()).signOut();
            }
        }

        setupCurrentLocation();
    }

    @SuppressLint("MissingPermission")
    private void updateMapToCurrentLocation() {
        // TODO
        Log.d(TAG, "Updating map to user location");
    }

    private void clearMapPins() {
        mMap.clear();
    }

    public void refreshMapPins() {
        String spinnerSelection = filterSpinner.getSelectedItem().toString();
        updateMapPins(spinnerSelection);
    }

    private void updateMapPins(String category) {
        // TODO: Complete with backend
        if (mMap == null || currentLocation == null) { return; }
        clearMapPins();
        // TODO - lat, lon, and distance, are stubs, please extract it from current location
        double lon = currentLocation.getLongitude();
        double lat = currentLocation.getLatitude();
        int distance = Integer.MAX_VALUE;
        Call<POI[]> callFilteredPOIs = findMyService.getFilteredPOIs(lon, lat, category, distance, currentUserId);

        callFilteredPOIs.enqueue(new Callback<POI[]>() {
            @Override
            public void onResponse(Call<POI[]> call, Response<POI[]> response) {
                POI[] filteredPOIs = response.body();
                if (filteredPOIs == null) { return; }
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
                .title(poi.getDescription());
        Marker marker = gMap.addMarker(markerOptions);
        // may need to clear tag upon clearing pin
        marker.setTag(poi);

        if (poi.isMyPOI()) {
            CircleOptions circleOptions = new CircleOptions()
                    .center(new LatLng(poi.getLatitude(), poi.getLongitude()))
                    .radius(poi.getRadius())
                    .fillColor(Color.parseColor("#2271cce7"))
                    .strokeColor(Color.parseColor("#55717de7"));


            Circle circle = gMap.addCircle(circleOptions);
        }
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        // TODO: testPOI
        MapPOIBottomSheet mapPoiBottomSheet = new MapPOIBottomSheet((POI) marker.getTag());

        mapPoiBottomSheet.show(requireActivity().getSupportFragmentManager(), TAG);
        return true;
    }

    @SuppressLint("MissingPermission")
    private void setupCurrentLocation() {
        currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }
}