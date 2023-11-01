package com.example.findmy.ui.friends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmy.model.User;
import com.example.findmy.databinding.FragmentFriendsBinding;

import java.util.ArrayList;

public class FriendsFragment extends Fragment {

    private FragmentFriendsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FriendsViewModel friendsViewModel =
                new ViewModelProvider(this).get(FriendsViewModel.class);

        binding = FragmentFriendsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayList<User> friendsArray = new ArrayList<>();

        User testUser = User.testUser;
        friendsArray.add(testUser);

        RecyclerView friendsRecycler = binding.friendsRecycler;
        FriendsAdapter adapter = new FriendsAdapter(requireActivity(), friendsArray);

        friendsRecycler.setAdapter(adapter);
        friendsRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}