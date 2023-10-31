package com.example.findmy.ui.map;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.findmy.R;
import com.example.findmy.databinding.FragmentAddPOIBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPOIFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPOIFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentAddPOIBinding binding;

    String newPOISelection;

    private final String TAG = "AddPOIFragment";

    public AddPOIFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPOIFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPOIFragment newInstance(String param1, String param2) {
        AddPOIFragment fragment = new AddPOIFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddPOIBinding.inflate(inflater, container, false);

        setupSpinner(binding);

        return binding.getRoot();
    }

    private void setupSpinner(FragmentAddPOIBinding binding) {
        Spinner newPOISpinner = binding.spinner;
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        newPOISelection = (String) parent.getItemAtPosition(position);
        Log.d(TAG, "New selection: " + newPOISelection);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        newPOISelection = null;
    }
}