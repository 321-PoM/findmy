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
import com.example.findmy.network.FindMyService;
import com.example.findmy.network.FindMyServiceViewModel;

import java.util.ArrayList;


public class FriendsFragment extends Fragment {

    private FragmentFriendsBinding binding;
    private FindMyService findMyService;
    private FriendsAdapter friendsAdapter;
    private ArrayList<User> friendsArray;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FriendsViewModel friendsViewModel =
                new ViewModelProvider(this).get(FriendsViewModel.class);

        findMyService = new ViewModelProvider(requireActivity()).get(FindMyServiceViewModel.class).getFindMyService();

        binding = FragmentFriendsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        retrieveFriends();

        RecyclerView friendsRecycler = binding.friendsRecycler;
        friendsAdapter = new FriendsAdapter(requireActivity(), friendsArray);

        friendsRecycler.setAdapter(friendsAdapter);
        friendsRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return root;
    }

    private void retrieveFriends() {
        friendsArray = new ArrayList<>();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}