package com.example.findmy.ui.map;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.findmy.R;
import com.example.findmy.model.POI;
import com.example.findmy.model.User;
import com.example.findmy.network.FindMyService;
import com.example.findmy.network.FindMyServiceViewModel;
import com.example.findmy.ui.HomeActivity;
import com.example.findmy.ui.LocationFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

public class MapsFragment extends LocationFragment implements AdapterView.OnItemSelectedListener, GoogleMap.OnMarkerClickListener {

    private final String TAG = "Map";

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
    private User currentCachedUser;
    private int currentUserId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        findMyService = new ViewModelProvider(requireActivity()).get(FindMyServiceViewModel.class).getFindMyService();

        HomeActivity homeActivity = (HomeActivity) requireActivity();
        setupLocationManager(homeActivity.locationManager);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        updateMapPins((String) parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        updateMapPins("All");
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
                if (!response.isSuccessful() && getContext() != null) {
                    String errMsg = findMyService.getErrorMessage(response);
                    Toast.makeText(requireContext(), errMsg, Toast.LENGTH_LONG).show();
                    return;
                }
                POI[] filteredPOIs = response.body();
                if (filteredPOIs == null) { return; }
                for (POI poi : filteredPOIs) {
                    placePOI(mMap, poi);
                }
            }

            @Override
            public void onFailure(Call<POI[]> call, Throwable t) {
                Toast.makeText(requireContext(), "Unable to update POI pins", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void placePOI(GoogleMap gMap, POI poi) {
        LatLng loc = new LatLng(poi.getLatitude(), poi.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions()
                .position(loc)
                .title(poi.getDescription());

        if (poi.isMyPOI()) {
            CircleOptions circleOptions = new CircleOptions()
                    .center(new LatLng(poi.getLatitude(), poi.getLongitude()))
                    .radius(poi.getRadius())
                    .fillColor(Color.parseColor("#2271cce7"))
                    .strokeColor(Color.parseColor("#55717de7"));
            Circle circle = gMap.addCircle(circleOptions);

            // myPOIs are blue
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        }
        Marker marker = gMap.addMarker(markerOptions);
        // may need to clear tag upon clearing pin
        marker.setTag(poi);
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        // TODO: testPOI
        MapPOIBottomSheet mapPoiBottomSheet = new MapPOIBottomSheet((POI) marker.getTag());

        mapPoiBottomSheet.show(requireActivity().getSupportFragmentManager(), TAG);
        return true;
    }
}