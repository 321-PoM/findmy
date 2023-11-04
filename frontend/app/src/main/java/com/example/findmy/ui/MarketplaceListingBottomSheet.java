package com.example.findmy.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.findmy.databinding.MarketplaceListingBottomSheetBinding;
import com.example.findmy.model.MarketListing;
import com.example.findmy.network.FindMyService;
import com.example.findmy.network.FindMyServiceViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarketplaceListingBottomSheet extends BottomSheetDialogFragment {

    MarketplaceListingBottomSheetBinding binding;
    private final MarketListing marketplaceListing;
    private FindMyService findMyService;


    MarketplaceListingBottomSheet(MarketListing marketplaceListing) {
        this.marketplaceListing = marketplaceListing;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        findMyService = new ViewModelProvider(requireActivity()).get(FindMyServiceViewModel.class).getFindMyService();

        binding = MarketplaceListingBottomSheetBinding.inflate(inflater, container, false);

        TextView poiNameText = binding.poiName;
        poiNameText.setText(String.valueOf(marketplaceListing.getId()));

        TextView listPriceText = binding.existingListingPriceText;
        listPriceText.setText(String.valueOf(marketplaceListing.getPrice()));

        TextView ownerNameText = binding.ownerName ;
        ownerNameText.setText(marketplaceListing.getSeller().getName());

        setupPurchaseButton(binding);

        return binding.getRoot();
    }

    private void setupPurchaseButton(MarketplaceListingBottomSheetBinding binding) {
        int currentUserId = ((HomeActivity) requireActivity()).getCurrentUserId();
        Button purchaseButton = binding.purchaseButton;
        View.OnClickListener purchaseButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findMyService.buyPoi(marketplaceListing.getPoi().getId(), currentUserId).enqueue(
                        new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(!response.isSuccessful() && getContext() != null) {
                                    findMyService.showErrorToast(requireContext());
                                    return;
                                }
                                dismiss();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                if(getContext() != null) findMyService.showErrorToast(requireContext());
                            }
                        }
                );
            }
        };

        purchaseButton.setOnClickListener(purchaseButtonListener);
    }

}
