package com.example.findmy.ui.friends;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.findmy.databinding.FriendsBottomSheetBinding;
import com.example.findmy.model.User;
import com.example.findmy.network.FindMyService;
import com.example.findmy.network.FindMyServiceViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FriendsBottomSheet extends BottomSheetDialogFragment {

    View.OnClickListener removeFriendButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(requireContext(), "Thanks for reporting!", Toast.LENGTH_SHORT).show();
            dismiss();
        }
    };
    private FindMyService findMyService;
    FriendsBottomSheetBinding binding;
    private final User friend;


    FriendsBottomSheet(User friend) {
        this.friend = friend;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        findMyService = new ViewModelProvider(requireActivity()).get(FindMyServiceViewModel.class).getFindMyService();

        binding = FriendsBottomSheetBinding.inflate(inflater, container, false);

        Button removeFriendButton = binding.removeFriendButton;
        removeFriendButton.setOnClickListener(removeFriendButtonListener);

        Log.d("FriendsBottomSheet", friend.getName());

        return binding.getRoot();
    }

}
