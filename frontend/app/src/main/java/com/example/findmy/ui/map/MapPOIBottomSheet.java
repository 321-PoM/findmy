package com.example.findmy.ui.map;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.findmy.databinding.PoiBottomSheetBinding;
import com.example.findmy.databinding.PopupPoiImageBinding;
import com.example.findmy.model.POI;
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
                                    String errMsg = findMyService.getErrorMessage(response);
                                    Toast.makeText(requireContext(), errMsg, Toast.LENGTH_LONG).show();
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

    private Button submitRatingButton;

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

        currentRatingBar.setRating(poi.getRating());

        inputRatingBar = binding.editRatingBar;

        submitRatingButton = binding.ratingSubmitButton;
        submitRatingButton.setOnClickListener(submitRatingListener);

        Button reportPOIButton = binding.reportButton;
        reportPOIButton.setOnClickListener(reportButtonListener);

        if (poi.isMyPOI()) {
            hideRatingViews();
        }

        setupImageButton(binding);

        setupDetails(binding);

        View root = binding.getRoot();
        return root;
    }

    private void hideRatingViews() {
        LinearLayout currentRatingLayout = binding.currentRatingLayout;
        currentRatingLayout.setVisibility(View.GONE);

        LinearLayout editRatingLayout = binding.editRatingLayout;
        editRatingLayout.setVisibility(View.GONE);

        submitRatingButton.setVisibility(View.GONE);
    }

    private void setupImageButton(PoiBottomSheetBinding binding) {
        Button viewImageButton = binding.viewImageButton;
        View.OnClickListener viewImageListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(requireContext());
                PopupPoiImageBinding popupBinding = PopupPoiImageBinding.inflate(inflater);
                View popupView = popupBinding.getRoot();

                ImageView detailsPoiImage = popupBinding.detailsPoiImage;

                Glide
                        .with(MapPOIBottomSheet.this)
                        .load(poi.getImageUrl())
                        .override(675, 1200)
                        .into(detailsPoiImage);

                detailsPoiImage.requestLayout();

                // refreshes the view such that the size is updated to the image
//                int visibility = popupView.getVisibility();
//                popupView.setVisibility(View.GONE);
//                popupView.setVisibility(visibility);

                // TODO: Error handling such that this isn't always invisible when a error occurs
//                TextView errorText = popupBinding.detailsPoiImageErrText;
//                errorText.setVisibility(View.GONE);

                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
            }
        };
        viewImageButton.setOnClickListener(viewImageListener);
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
