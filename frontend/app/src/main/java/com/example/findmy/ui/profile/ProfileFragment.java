package com.example.findmy.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmy.model.POI;
import com.example.findmy.model.User;
import com.example.findmy.network.FindMyService;
import com.example.findmy.network.FindMyServiceViewModel;
import com.example.findmy.ui.HomeActivity;
import com.example.findmy.databinding.FragmentProfileBinding;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {

    private final String TAG = "ProfileFragment";

    private FindMyService findMyService;

    private FragmentProfileBinding binding;
    private ArrayList<POI> myPOIList;
    private MyPOIListAdapter mPOIAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        findMyService = new ViewModelProvider(requireActivity()).get(FindMyServiceViewModel.class).getFindMyService();

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
        retrieveMyPOIs();

        POI testPOI = POI.testPOI;
        myPOIList.add(testPOI);

        // TODO: update with live location
        LatLng currentLatLng = new LatLng(0.0, 0.0);
        RecyclerView myPOIRecycler = binding.myPOIRecycler;
        mPOIAdapter = new MyPOIListAdapter(requireActivity(), myPOIList, currentLatLng);

        myPOIRecycler.setAdapter(mPOIAdapter);
        myPOIRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));

        setupUsernameText(binding);

        return root;
    }

    private void retrieveMyPOIs() {
        myPOIList = new ArrayList<POI>();
        Call<POI[]> call = findMyService.getPOIs();
        call.enqueue(new Callback<POI[]>() {
            @Override
            public void onResponse(Call<POI[]> call, Response<POI[]> response) {
                POI[] retrievedPOIs = response.body();
                for (POI p : retrievedPOIs) {
                    if (p.getCategory() == "myPOI") {
                        myPOIList.add(p);
                    }
                }
                mPOIAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<POI[]> call, Throwable t) {
                Toast.makeText(requireContext(), "Error: Unable to retrieve myPOIs", Toast.LENGTH_SHORT)
                        .show();
            }
        });
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

    private void setupUsernameText(FragmentProfileBinding binding) {
        TextView usernameText = binding.usernameText;

        HomeActivity homeActivity = (HomeActivity) requireActivity();
        User currentUser = homeActivity.currentUser;
        usernameText.setText(currentUser.getName());
    }

}