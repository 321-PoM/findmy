package com.example.findmy.ui.friends;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmy.R;
import com.example.findmy.model.Friend;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    FragmentActivity parentActivity;

    private List<Friend> friends;
    public FriendsAdapter(FragmentActivity parentActivity, List<Friend> friends) {
        this.parentActivity = parentActivity;
        this.friends = friends;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public Button viewDetailsButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = (TextView) itemView.findViewById(R.id.friendName);
            viewDetailsButton = (Button) itemView.findViewById(R.id.viewFriendButton);
        }
    }

    @NonNull
    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View friendView = layoutInflater.inflate(R.layout.friends_list_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(friendView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsAdapter.ViewHolder holder, int position) {
        Friend friend = friends.get(position);

        TextView textView = holder.nameText;
        textView.setText(friend.getName());

//        TextView pendingText = holder.
//
//        if (friend.isConfirmed()) {
//
//        }

        // TODO: set button functionality
        Button button = holder.viewDetailsButton;
        button.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FriendsBottomSheet bottomSheet = new FriendsBottomSheet(friend);
                    bottomSheet.show(parentActivity.getSupportFragmentManager(), "TEST_TAG");
                }
           }
        );
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
}
