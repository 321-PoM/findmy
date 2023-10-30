package com.example.findmy.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmy.R;

import java.util.List;

public class MarketplaceListingAdapter extends RecyclerView.Adapter<MarketplaceListingAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public TextView distanceText;
        public Button viewDetailsButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = (TextView) itemView.findViewById(R.id.myPOINameText);
            distanceText = (TextView) itemView.findViewById(R.id.myPOIDistance);
            viewDetailsButton = (Button) itemView.findViewById(R.id.viewDetailsButton);
        }
    }

    private List<MarketplaceListing> listings;
    public MarketplaceListingAdapter(List<MarketplaceListing> listings) {
        this.listings = listings;
    }

    @NonNull
    @Override
    public MarketplaceListingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View marketplaceView = layoutInflater.inflate(R.layout.mypoi_list_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(marketplaceView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MarketplaceListingAdapter.ViewHolder holder, int position) {
        MarketplaceListing listing = listings.get(position);

        TextView nameText = holder.nameText;
        nameText.setText(listing.getListingName());

        TextView distanceText = holder.distanceText;
        // TODO: get distance
        distanceText.setText("100m");

        // TODO: set button functionality
        Button button = holder.viewDetailsButton;
    }

    @Override
    public int getItemCount() {
        return listings.size();
    }
}
