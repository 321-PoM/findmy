package com.example.findmy.ui;

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
import com.example.findmy.network.FindMyService;
import com.example.findmy.network.FindMyServiceViewModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MarketplaceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MarketplaceFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentMarketplaceBinding binding;
    private FindMyService findMyService;
    private ArrayList<MarketListing> listingArray;
    private MarketplaceListingAdapter marketplaceListingAdapter;

    public MarketplaceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MarketplaceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MarketplaceFragment newInstance(String param1, String param2) {
        MarketplaceFragment fragment = new MarketplaceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // retrieve findMyService from parent activity
        findMyService = new ViewModelProvider(requireActivity()).get(FindMyServiceViewModel.class).getFindMyService();

        // Inflate the layout for this fragment
        binding = FragmentMarketplaceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        retrieveListings();

        // TODO: update with live location
        LatLng currentLatLng = new LatLng(0.0, 0.0);
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
                marketplaceListingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MarketListing[]> call, Throwable t) {
                Toast.makeText(requireContext(), "Error: Unable to retrieve Marketplace listings", Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }
}