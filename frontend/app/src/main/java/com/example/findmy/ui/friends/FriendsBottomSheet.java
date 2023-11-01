package com.example.findmy.ui.friends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.findmy.databinding.FriendsBottomSheetBinding;
import com.example.findmy.model.User;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FriendsBottomSheet extends BottomSheetDialogFragment {

    View.OnClickListener removeFriendButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO: add calls to backend
        }
    };
    FriendsBottomSheetBinding binding;
    private final User friend;


    FriendsBottomSheet(User friend) {
        this.friend = friend;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FriendsBottomSheetBinding.inflate(inflater, container, false);

        Button removeFriendButton = binding.removeFriendButton;
        removeFriendButton.setOnClickListener(removeFriendButtonListener);

        return binding.getRoot();
    }

}
