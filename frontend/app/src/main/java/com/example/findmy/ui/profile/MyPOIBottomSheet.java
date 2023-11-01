package com.example.findmy.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.findmy.model.POI;
import com.example.findmy.databinding.ProfilePoiBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MyPOIBottomSheet extends BottomSheetDialogFragment {
    private final View.OnClickListener submitListingListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO: add calls to backend
        }
    };

    private final View.OnClickListener unlistListingListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO: add calls to backend
        }
    };

    ProfilePoiBottomSheetBinding binding;

    private final POI myPOI;

    MyPOIBottomSheet(POI myPOI) {
        this.myPOI = myPOI;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ProfilePoiBottomSheetBinding.inflate(inflater, container, false);

        TextView myPOINameText = binding.poiName;
        myPOINameText.setText(this.myPOI.getDescription());

        return binding.getRoot();
    }
}