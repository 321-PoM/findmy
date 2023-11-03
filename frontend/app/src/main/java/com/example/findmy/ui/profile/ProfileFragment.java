package com.example.findmy.ui.profile;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationManager;
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

import com.example.findmy.model.MapBuxRequest;
import com.example.findmy.model.MapBuxResponse.MapBuxResponse;
import com.example.findmy.model.POI;
import com.example.findmy.model.User;
import com.example.findmy.network.FindMyService;
import com.example.findmy.network.FindMyServiceViewModel;
import com.example.findmy.ui.HomeActivity;
import com.example.findmy.databinding.FragmentProfileBinding;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {

    private final String TAG = "ProfileFragment";

    private FindMyService findMyService;

    private FragmentProfileBinding binding;
    private ArrayList<POI> myPOIList;
    private MyPOIListAdapter mPOIAdapter;
    private User currentUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        findMyService = new ViewModelProvider(requireActivity()).get(FindMyServiceViewModel.class).getFindMyService();

        HomeActivity homeActivity = (HomeActivity) requireActivity();
        currentUser = homeActivity.currentUser;

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
        retrieveMyPOIsAndUpdateRecycler();

        // TODO: update with live location
        setupEmailText(binding);

        setupMapBuxText(binding);

        setupGetMapBuxButton(binding);

        return root;
    }

    private void setupGetMapBuxButton(FragmentProfileBinding binding) {
        binding.getMapBuxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findMyService.updateUserMapBux(currentUser.getId(), new MapBuxRequest(true, 100)).enqueue(new Callback<MapBuxResponse>() {
                    @Override
                    public void onResponse(Call<MapBuxResponse> call, Response<MapBuxResponse> response) {
                       if (!response.isSuccessful()) {
                           findMyService.showErrorToast(requireContext());
                           return;
                       }
                       updateMapBuxText(binding, response.body().getMapBux());
                    }

                    @Override
                    public void onFailure(Call<MapBuxResponse> call, Throwable t) {
                        findMyService.showErrorToast(requireContext());
                    }
                });
            }
        });
    }

    private void setupMapBuxText(FragmentProfileBinding binding) {
       TextView mapbuxText = binding.mapBuxAmountText;
       mapbuxText.setText(String.valueOf(currentUser.getMapBux()));
    }

    private void updateMapBuxText(FragmentProfileBinding binding, int amount) {
        TextView mapBuxText = binding.mapBuxAmountText;
        mapBuxText.setText(String.valueOf(amount));
    }

    private void setupRecycler(FragmentProfileBinding binding, List<POI> pois, LatLng currentLatLng) {
        RecyclerView myPOIRecycler = binding.myPOIRecycler;
        mPOIAdapter = new MyPOIListAdapter(requireActivity(), myPOIList, currentLatLng);

        myPOIRecycler.setAdapter(mPOIAdapter);
        myPOIRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));

    }

    private void retrieveMyPOIsAndUpdateRecycler() {
        myPOIList = new ArrayList<POI>();
        Call<POI[]> call = findMyService.getPOIs();
        call.enqueue(new Callback<POI[]>() {
            @Override
            public void onResponse(Call<POI[]> call, Response<POI[]> response) {
                POI[] retrievedPOIs = response.body();
                for (POI p : retrievedPOIs) {
                    Log.d(TAG, p.getCategory());
                    if (p.getCategory().equals("myPOI") && p.getOwnderId() == currentUser.getId()) {
                        myPOIList.add(p);
                    }
                }
                @SuppressLint("MissingPermission") Location currentLocation = ((HomeActivity) requireActivity()).locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                setupRecycler(binding, myPOIList, currentLatLng);
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

    private void setupEmailText(FragmentProfileBinding binding) {
        TextView emailText = binding.emailText;
        emailText.setText(currentUser.getEmail());
    }

}