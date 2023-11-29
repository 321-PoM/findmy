package com.example.findmy.ui.map;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPOIBottomSheet extends BottomSheetDialogFragment implements  AdapterView.OnItemSelectedListener {

    AddPoiBottomSheetBinding binding;
    RatingBar inputRatingBar;

    private final String TAG = "AddPOIBottomSheet";

    Location currLocation;

    private View.OnClickListener submitNewPOIListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String poiName = getInputPOIName();
            String poiType = getNewPOIType();
            int rating = (int) inputRatingBar.getRating();

            int currentUserId = ((HomeActivity) requireActivity()).getCurrentUserId();

            POIRequest newPOI = new POIRequest(currLocation.getLatitude(), currLocation.getLongitude(), poiType, "verified", poiName, currentUserId, rating, 0, false, new File("error"));

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
    private FindMyService findMyService;
    private EditText nameTextField;
    private Button button;
    private String newPOITypeSelection;

    public AddPOIBottomSheet(Location currLocation) {
        this.currLocation = currLocation;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        findMyService = new ViewModelProvider(requireActivity()).get(FindMyServiceViewModel.class).getFindMyService();

        binding = AddPoiBottomSheetBinding.inflate(inflater, container, false);

        setupTextField(binding);

        inputRatingBar = binding.ratingBar;

        setupButton(binding);

        setupSpinner(binding);

        View root = binding.getRoot();
        return root;
    }

    private void setupTextField(AddPoiBottomSheetBinding binding) {
        nameTextField = binding.editTextText;
    }

    private void setupButton(AddPoiBottomSheetBinding binding) {
        button = binding.button;
        button.setOnClickListener(submitNewPOIListener);
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
}
