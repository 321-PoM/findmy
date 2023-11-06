package com.example.findmy.ui;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.findmy.model.MarketListing;
import com.example.findmy.databinding.FragmentMarketplaceBinding;
import com.example.findmy.model.POIComparator;
import com.example.findmy.network.FindMyService;
import com.example.findmy.network.FindMyServiceViewModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarketplaceFragment extends Fragment {

    private FindMyService findMyService;
    private ArrayList<MarketListing> listingArray;
    private MarketplaceListingAdapter marketplaceListingAdapter;
    private LatLng currentLatLng;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // retrieve findMyService from parent activity
        findMyService = new ViewModelProvider(requireActivity()).get(FindMyServiceViewModel.class).getFindMyService();

        // Inflate the layout for this fragment
        FragmentMarketplaceBinding binding = FragmentMarketplaceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        retrieveListings();

        LocationManager locationManager = ((HomeActivity) requireActivity()).locationManager;
        @SuppressLint("MissingPermission") Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        currentLatLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
        RecyclerView listingsRecylcer = binding.listingsRecylcer;
        marketplaceListingAdapter = new MarketplaceListingAdapter(requireActivity(), listingArray, currentLatLng);

        listingsRecylcer.setAdapter(marketplaceListingAdapter);
        listingsRecylcer.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return root;
    }

    private void retrieveListings() {
        listingArray = new ArrayList<>();
        Call<MarketListing[]> call = findMyService.getListings();

        call.enqueue(new Callback<MarketListing[]>() {
            @Override
            public void onResponse(Call<MarketListing[]> call, Response<MarketListing[]> response) {
                MarketListing[] retrievedListings = response.body();
                listingArray.addAll(Arrays.asList(retrievedListings));

                listingArray.sort(new Comparator<MarketListing>() {
                    @Override
                    public int compare(MarketListing o1, MarketListing o2) {
                        POIComparator poiComparator = new POIComparator(currentLatLng);
                        return poiComparator.compare(o1.getPoi(), o2.getPoi());
                    }
                });

                marketplaceListingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MarketListing[]> call, Throwable t) {
                if(getContext() != null) Toast.makeText(requireContext(), "Error: Unable to retrieve Marketplace listings", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}