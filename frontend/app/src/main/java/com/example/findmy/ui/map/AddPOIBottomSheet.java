package com.example.findmy.ui.map;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.findmy.R;
import com.example.findmy.databinding.AddPoiBottomSheetBinding;
import com.example.findmy.model.POI;
import com.example.findmy.model.POIRequest;
import com.example.findmy.network.FindMyService;
import com.example.findmy.network.FindMyServiceViewModel;
import com.example.findmy.ui.HomeActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPOIBottomSheet extends BottomSheetDialogFragment implements  AdapterView.OnItemSelectedListener {

    private static final int CAMERA_PERMISSION_REQUEST = 0;
    AddPoiBottomSheetBinding binding;
    RatingBar inputRatingBar;

    private final String TAG = "AddPOIBottomSheet";

    private final int CAMERA_REQ_CODE = 1000;

    Bitmap myPOIImage = null;

    Location currLocation;

    private final View.OnClickListener submitNewPOIListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String poiName = getInputPOIName();
            String poiType = getNewPOIType();
            int rating = (int) inputRatingBar.getRating();

            int currentUserId = ((HomeActivity) requireActivity()).getCurrentUserId();

            File poiImageFile;
            OutputStream poiImageStream;
            try {
                poiImageFile = File.createTempFile("poi", ".jpeg");
                poiImageStream = new FileOutputStream(poiImageFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SecurityException e) {
                throw new RuntimeException(e);
            }


            if (myPOIImage != null) {
                myPOIImage.compress(Bitmap.CompressFormat.JPEG, 100 , poiImageStream);
                try {
                    poiImageStream.flush();
                    poiImageStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            POIRequest newPOI = new POIRequest(currLocation.getLatitude(), currLocation.getLongitude(), poiType, "verified", poiName, currentUserId, rating,  poiImageFile);

            findMyService.createPOI(newPOI).enqueue(
                    new Callback<POI>() {
                        @Override
                        public void onResponse(Call<POI> call, Response<POI> response) {
                            if (getContext() != null){
                                if (!response.isSuccessful()) {
                                    findMyService.showErrorToast(requireContext());
                                    return;
                                }
                                Toast.makeText(AddPOIBottomSheet.this.requireContext(), "Submitted", Toast.LENGTH_LONG);
                                dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<POI> call, Throwable t) {
                            if(getContext() != null) findMyService.showErrorToast(requireContext());
                        }
                    }
            );

            Log.d(TAG, "Submitting form: poiName " + poiName + "; poiType " + poiType + "; rating: " + rating);
        }
    };

    private View.OnClickListener addImageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!checkCameraPermissions()) {
                Toast.makeText(
                        requireContext(), "Camera Permissions are Required!", Toast.LENGTH_LONG
                );
                requestCameraPermissions();
            } else {
                launchCameraIntent();
            }
        }
    };

    private FindMyService findMyService;
    private EditText nameTextField;
    private Button submitButton;
    private String newPOITypeSelection;
    private Button addImageButton;
    private ImageView poiImage;

    public AddPOIBottomSheet(Location currLocation) {
        this.currLocation = currLocation;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // get camera permissions


        findMyService = new ViewModelProvider(requireActivity()).get(FindMyServiceViewModel.class).getFindMyService();

        binding = AddPoiBottomSheetBinding.inflate(inflater, container, false);

        setupTextField(binding);

        inputRatingBar = binding.ratingBar;

        setupPOIImage(binding);

        setupSubmitButton(binding);

        setupAddImageButton(binding);

        setupSpinner(binding);

        View root = binding.getRoot();
        return root;
    }

    private boolean checkCameraPermissions() {
        return ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermissions() {
        ActivityCompat.requestPermissions(
                requireActivity(),
                new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
    }

    private void setupTextField(AddPoiBottomSheetBinding binding) {
        nameTextField = binding.editTextText;
    }

    private void setupSubmitButton(AddPoiBottomSheetBinding binding) {
        submitButton = binding.submitButton;
        submitButton.setOnClickListener(submitNewPOIListener);
    }

    private void setupAddImageButton(AddPoiBottomSheetBinding binding) {
        addImageButton = binding.addImageButton;
        addImageButton.setOnClickListener(addImageListener);
    }

    private void setupPOIImage(AddPoiBottomSheetBinding binding) {
        poiImage = binding.poiImage;
    }

    private void updatePOIImage() {
        if (poiImage != null && myPOIImage != null) {
            poiImage.setImageBitmap(myPOIImage);
        }
    }

    private void setupSpinner(AddPoiBottomSheetBinding binding) {
        AdapterView newPOISpinner = binding.spinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.new_poi_choices,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        newPOISpinner.setSelection(0);
        newPOISpinner.setAdapter(adapter);
        newPOISpinner.setOnItemSelectedListener(this);
    }

    private String getInputPOIName() {
        return nameTextField.getText().toString();
    }

    private String getNewPOIType() {
        return newPOITypeSelection;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        newPOITypeSelection = (String) parent.getItemAtPosition(position);
        Log.d(TAG, "New selection: " + newPOITypeSelection);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        newPOITypeSelection = null;
    }

    private void launchCameraIntent() {
        Intent galleryIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(galleryIntent, CAMERA_REQ_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (checkCameraPermissions()) {
                launchCameraIntent();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK) {
            if (requestCode== CAMERA_REQ_CODE) {
                myPOIImage = (Bitmap) data.getExtras().get("data");
                poiImage.setImageBitmap(myPOIImage);
            }
        }
    }
}
