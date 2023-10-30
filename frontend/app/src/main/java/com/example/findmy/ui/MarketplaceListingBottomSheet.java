package com.example.findmy.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.findmy.databinding.MarketplaceListingBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MarketplaceListingBottomSheet extends BottomSheetDialogFragment {

    View.OnClickListener purchaseButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO: add calls to backend
        }
    };

    MarketplaceListingBottomSheetBinding binding;
    private final MarketplaceListing marketplaceListing;


    MarketplaceListingBottomSheet(MarketplaceListing marketplaceListing) {
        this.marketplaceListing = marketplaceListing;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MarketplaceListingBottomSheetBinding.inflate(inflater, container, false);

        TextView poiNameText = binding.poiName;
        poiNameText.setText(marketplaceListing.getListingName());

        TextView listPriceText = binding.listingPrice;
        listPriceText.setText(String.valueOf(marketplaceListing.getListingAmount()));

        TextView ownerNameText = binding.ownerName ;
        ownerNameText.setText(marketplaceListing.getOwner().getUsername());

        Button purchaseButton = binding.purchaseButton;
        purchaseButton.setOnClickListener(purchaseButtonListener);

        return binding.getRoot();
    }

}
