package com.example.findmy.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findmy.POI.POI;
import com.example.findmy.databinding.FragmentMarketplaceBinding;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

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
        // Inflate the layout for this fragment


        binding = FragmentMarketplaceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ArrayList<MarketplaceListing> listingArray = new ArrayList<>();

        POI testPOI = POI.testPOI;
        MarketplaceListing testListing = new MarketplaceListing(18, testPOI);
        listingArray.add(testListing);

        // TODO: update with live location
        LatLng currentLatLng = new LatLng(0.0, 0.0);
        RecyclerView listingsRecylcer = binding.listingsRecylcer;
        MarketplaceListingAdapter adapter = new MarketplaceListingAdapter(requireActivity(), listingArray, currentLatLng);

        listingsRecylcer.setAdapter(adapter);
        listingsRecylcer.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return root;
    }
}