package com.example.findmy.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.findmy.POI.POI;
import com.example.findmy.databinding.PoiBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class POIBottomSheet extends BottomSheetDialogFragment {

    PoiBottomSheetBinding binding;
    private final POI poi;

    private View.OnClickListener submitRatingListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO: call backend handler
        }
    };

    public POIBottomSheet(POI poi) {
        this.poi = poi;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PoiBottomSheetBinding.inflate(inflater, container, false);

        TextView poiNameText = binding.poiName;
        poiNameText.setText(poi.getName());

        RatingBar currentRatingBar = binding.currentRatingBar;
        // TODO: update with POI rating
        currentRatingBar.setRating((float) 3.5);

        Button submitRatingButton = binding.ratingSubmitButton;
        submitRatingButton.setOnClickListener(submitRatingListener);

        View root = binding.getRoot();
        return root;
    }
}
