package com.example.findmy.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.findmy.model.POI;
import com.example.findmy.databinding.PoiBottomSheetBinding;
import com.example.findmy.model.Review;
import com.example.findmy.model.ReviewRequest;
import com.example.findmy.model.User;
import com.example.findmy.network.FindMyService;
import com.example.findmy.network.FindMyServiceViewModel;
import com.example.findmy.ui.HomeActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapPOIBottomSheet extends BottomSheetDialogFragment {

    PoiBottomSheetBinding binding;
    private final POI poi;

    RatingBar inputRatingBar;

    private View.OnClickListener submitRatingListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int rating = (int) inputRatingBar.getRating();

            int currentUserId = ((HomeActivity) requireActivity()).getCurrentUserId();

            ReviewRequest request = new ReviewRequest(currentUserId, poi.getId(), rating);

            findMyService.createReview(request).enqueue(
                    new Callback<Review>() {
                        public void onResponse(Call<Review> call, Response<Review> response) {
                            if(getContext() != null) {
                                if (!response.isSuccessful()) {
                                    findMyService.showErrorToast(requireContext());
                                    return;
                                }
                                Toast.makeText(MapPOIBottomSheet.this.requireContext(), "Submitted", Toast.LENGTH_LONG).show();
                                dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<Review> call, Throwable t) {
                            if(getContext() != null)  findMyService.showErrorToast(requireContext());
                        }
                    }
            );
        }
    };

    private final View.OnClickListener reportButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            findMyService.reportPOI(poi.getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(getContext() != null) {
                        if (!response.isSuccessful()) {
                            findMyService.showErrorToast(requireContext());
                            return;
                        }
                        Toast.makeText(requireContext(), "Thank you for reporting", Toast.LENGTH_SHORT)
                                .show();
                        dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    if(getContext() != null) findMyService.showErrorToast(requireContext());
                }
            });
            // TODO: call backend handler
        }
    };

    private FindMyService findMyService;

    public MapPOIBottomSheet(POI poi) {
        this.poi = poi;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        findMyService = new ViewModelProvider(requireActivity()).get(FindMyServiceViewModel.class).getFindMyService();

        binding = PoiBottomSheetBinding.inflate(inflater, container, false);

        TextView poiNameText = binding.poiName;
        poiNameText.setText(poi.getDescription());

        RatingBar currentRatingBar = binding.currentRatingBar;
        // TODO: update with POI rating
        currentRatingBar.setRating(poi.getRating());

        inputRatingBar = binding.editRatingBar;

        Button submitRatingButton = binding.ratingSubmitButton;
        submitRatingButton.setOnClickListener(submitRatingListener);

        Button reportPOIButton = binding.reportButton;
        reportPOIButton.setOnClickListener(reportButtonListener);

        setupDetails(binding);

        View root = binding.getRoot();
        return root;
    }

    private void setupDetails(PoiBottomSheetBinding binding) {
        TextView ratingHeaderText = binding.currentRatingHeaderText;
        String headerText = "Current ";
        switch(poi.getCategory()) {
            case "Washroom":
                headerText += "Cleanliness";
                break;
            case "Microwave":
                headerText += "Cleanliness";
                break;
            case "Study Space":
                headerText += "Busyness";
                break;
            default:
                headerText += "Rating";
                break;
        }

        ratingHeaderText.setText(headerText);

        TextView poiTypeText = binding.poiTypeText;
        poiTypeText.setText(poi.getCategory());

        TextView poiOwnerText = binding.poiOwnerText;

        findMyService.getUser(poi.getOwnderId()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(requireContext(), "Could not get owner name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                User poiOwner = response.body();
                poiOwnerText.setText(poiOwner.getName());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(requireContext(), "Could not get owner name!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
