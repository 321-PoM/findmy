package com.example.findmy.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmy.model.POI;
import com.example.findmy.ui.HomeActivity;
import com.example.findmy.databinding.FragmentProfileBinding;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {

    private final String TAG = "ProfileFragment";

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button signOutButton = binding.profileSignOutButton;
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        // setup myPOI list
        ArrayList<POI> myPOIList = new ArrayList<POI>();

        POI testPOI = POI.testPOI;
        myPOIList.add(testPOI);

        // TODO: update with live location
        LatLng currentLatLng = new LatLng(0.0, 0.0);
        RecyclerView myPOIRecycler = binding.myPOIRecycler;
        MyPOIListAdapter mAdapter = new MyPOIListAdapter(requireActivity(), myPOIList, currentLatLng);

        myPOIRecycler.setAdapter(mAdapter);
        myPOIRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void signOut() {
        Log.d(TAG, "Signing Out!");

        HomeActivity activity = (HomeActivity) getActivity();
        if (activity != null) {
            activity.signOut();
        }
    }

}