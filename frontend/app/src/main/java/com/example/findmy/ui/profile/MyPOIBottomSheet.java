package com.example.findmy.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.findmy.model.MarketListing;
import com.example.findmy.model.MarketListingRequest;
import com.example.findmy.model.POI;
import com.example.findmy.databinding.ProfilePoiBottomSheetBinding;
import com.example.findmy.network.FindMyService;
import com.example.findmy.network.FindMyServiceViewModel;
import com.example.findmy.ui.HomeActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPOIBottomSheet extends BottomSheetDialogFragment {
    private final View.OnClickListener submitListingListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int currentUserId = ((HomeActivity) requireActivity()).getCurrentUserId();
            int listingPrice;
            try {
                listingPrice = getListingPrice();
            } catch (NumberFormatException e) {
                if(getContext() != null) Toast.makeText(requireContext(), "Invalid Price - Enter a different value", Toast.LENGTH_LONG).show();
                return;
            }
            MarketListingRequest request = new MarketListingRequest(listingPrice, currentUserId, myPOI.getId(), true, false);
            findMyService.createListing(request).enqueue(new Callback<MarketListing>() {
                @Override
                public void onResponse(Call<MarketListing> call, Response<MarketListing> response) {
                    if(!response.isSuccessful()) {
                        if(getContext() != null) findMyService.showErrorToast(requireContext());
                        return;
                    }
                    dismiss();
                }

                @Override
                public void onFailure(Call<MarketListing> call, Throwable t) {
                    if(getContext() != null) findMyService.showErrorToast(requireContext());
                }
            });
        }
    };

    ProfilePoiBottomSheetBinding binding;

    private final POI myPOI;
    private FindMyService findMyService;
    private EditText inputListingPriceText;

    MyPOIBottomSheet(POI myPOI) {
        this.myPOI = myPOI;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        findMyService = new ViewModelProvider(requireActivity()).get(FindMyServiceViewModel .class).getFindMyService();

        binding = ProfilePoiBottomSheetBinding.inflate(inflater, container, false);

        setupPOIName(binding);
        setupSubmitListingButton(binding);
        setupListingPriceInput(binding);

        setupBasedOnExistingListing();

        return binding.getRoot();
    }

    private void setupPOIName(ProfilePoiBottomSheetBinding binding) {
        TextView myPOINameText = binding.poiName;
        myPOINameText.setText(this.myPOI.getDescription());
    }

    private void setupSubmitListingButton(ProfilePoiBottomSheetBinding binding) {
        binding.listButton.setOnClickListener(submitListingListener);
    }

    private void setupUnlistButton(ProfilePoiBottomSheetBinding binding, int listingId) {
        View.OnClickListener unlistListingListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findMyService.deleteListing(listingId).enqueue(new Callback<MarketListing>() {
                    @Override
                    public void onResponse(Call<MarketListing> call, Response<MarketListing> response) {
                        if (!response.isSuccessful() && getContext() != null) {
                            findMyService.showErrorToast(requireContext());
                            return;
                        }
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<MarketListing> call, Throwable t) {
                        if(getContext() != null) findMyService.showErrorToast(requireContext());
                    }
                });
            }
        };
        binding.unlistButton.setOnClickListener(unlistListingListener);
    }

    private void setupListingPriceInput(ProfilePoiBottomSheetBinding binding) {
        inputListingPriceText = binding.inputListingPrice;
    }

    private int getListingPrice() throws NumberFormatException {
        String listingPrice = String.valueOf(inputListingPriceText.getText());
        return Integer.parseInt(listingPrice);
    }

    private void checkIfListingExists(Callback<MarketListing[]> callback) {
        int id = myPOI.getId();
        findMyService.getMarketListingsByPoi(id).enqueue(callback);
    }

    private void setupBasedOnExistingListing() {
        checkIfListingExists(new Callback<MarketListing[]>() {
            @Override
            public void onResponse(Call<MarketListing[]> call, Response<MarketListing[]> response) {
                if(!response.isSuccessful() && getContext() != null) {
                    findMyService.showErrorToast(requireContext());
                    hideLayoutsForExistingListing();
                    hideLayoutsForNewListing();
                    return;
                }

                MarketListing[] results = response.body();
                if (results.length > 0) {
                    hideLayoutsForExistingListing();

                    MarketListing result = results[0];

                    int price = result.getPrice();
                    setupUnlistButton(binding, result.getId());
                    updateExistingListingPrice(price);
                } else {
                    hideLayoutsForNewListing();
                }
            }

            @Override
            public void onFailure(Call<MarketListing[]> call, Throwable t) {
                if(getContext() != null) findMyService.showErrorToast(requireContext());
            }
        });
    }

    private void updateExistingListingPrice(int price) {
        binding.existingListingPriceText.setText(String.valueOf(price));
    }

    private void hideLayoutsForExistingListing() {
        View createListingLayout = binding.createListingLayout;
        createListingLayout.setVisibility(View.GONE);
    }

    private void hideLayoutsForNewListing() {
        View editListingLayout = binding.unlistLayout;
        editListingLayout.setVisibility(View.GONE);
    }
}