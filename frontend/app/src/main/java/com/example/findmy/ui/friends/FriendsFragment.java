package com.example.findmy.ui.friends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmy.model.Friendship;
import com.example.findmy.model.FriendshipRequest;
import com.example.findmy.model.User;
import com.example.findmy.databinding.FragmentFriendsBinding;
import com.example.findmy.network.FindMyService;
import com.example.findmy.network.FindMyServiceViewModel;
import com.example.findmy.ui.HomeActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FriendsFragment extends Fragment {

    private FragmentFriendsBinding binding;
    private FindMyService findMyService;
    private FriendsAdapter friendsAdapter;
    private ArrayList<User> friendsArray;
    private Button addButton;

    private final View.OnClickListener addFriendListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = getFriendTextField();
            if((text == null || text.isEmpty()) && getContext() != null){
                Toast.makeText(requireContext(), "Invalid Input - Enter a different value", Toast.LENGTH_LONG).show();
                return;
            }
            initiateFriendRequest(text);
        }
    };
    private EditText friendTextField;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FriendsViewModel friendsViewModel =
                new ViewModelProvider(this).get(FriendsViewModel.class);

        findMyService = new ViewModelProvider(requireActivity()).get(FindMyServiceViewModel.class).getFindMyService();

        binding = FragmentFriendsBinding.inflate(inflater, container, false);

        retrieveFriends();
        setupAddButton(binding);

        setupRecycler(binding);
        setupFriendTextField(binding);

        View root = binding.getRoot();
        return root;
    }

    private void addFriendWithID(int friendID) {
        User currentUser = ((HomeActivity) requireActivity()).currentUser;
        FriendshipRequest request = new FriendshipRequest(currentUser.getId(), friendID);
        findMyService.createFriendship(request).enqueue(
                new Callback<Friendship>() {
                    @Override
                    public void onResponse(Call<Friendship> call, Response<Friendship> response) {
                        if(!response.isSuccessful() && getContext() != null) {
                            findMyService.showErrorToast(requireContext());
                        }
                        // TODO: update UI?
                    }

                    @Override
                    public void onFailure(Call<Friendship> call, Throwable t) {
                        if(getContext() != null) findMyService.showErrorToast(requireContext());
                    }
                }
        );
    }

    private void initiateFriendRequest(String email) {
        findMyService.getUserByEmail(email).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful() && getContext() != null) {
                    findMyService.showErrorToast(requireContext());
                }
                addFriendWithID(response.body().getId());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if(getContext() != null) findMyService.showErrorToast(requireContext());
            }
        });
    }

    private void setupFriendTextField(FragmentFriendsBinding binding) {
        friendTextField = binding.addFriendField;
    }

    private String getFriendTextField() {
        return String.valueOf(friendTextField.getText());
    }

    private void setupAddButton(FragmentFriendsBinding binding) {
        addButton = binding.addFriendButton;
        addButton.setOnClickListener(addFriendListener);
    }

    private void setupRecycler(FragmentFriendsBinding binding) {
        RecyclerView friendsRecycler = binding.friendsRecycler;
        friendsAdapter = new FriendsAdapter(requireActivity(), friendsArray);

        friendsRecycler.setAdapter(friendsAdapter);
        friendsRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
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