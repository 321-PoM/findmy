package com.example.findmy.ui;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmy.model.MarketListing;
import com.example.findmy.model.POI;
import com.example.findmy.R;
import com.google.android.gms.maps.model.LatLng;

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

    LatLng userLocation;
    private List<MarketListing> listings;

    private FragmentActivity parentActivity;
    private static final float maxDistanceToDisplay = (float) 2000.0;

    public MarketplaceListingAdapter(FragmentActivity parentActivity, List<MarketListing> listings, LatLng userLocation) {
        this.listings = listings;
        this.userLocation = userLocation;
        this.parentActivity = parentActivity;
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
        MarketListing listing = listings.get(position);

        TextView nameText = holder.nameText;
        nameText.setText(String.valueOf(listing.getPoi().getDescription()));

        TextView distanceText = holder.distanceText;

        // TODO get real POI from listing
        distanceText.setText(getDistanceFromUserToPOI(listing.getPoi(), this.userLocation));

        Button button = holder.viewDetailsButton;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarketplaceListingBottomSheet bottomSheet = new MarketplaceListingBottomSheet(listing);
                bottomSheet.show(parentActivity.getSupportFragmentManager(), "TEST_TAG");
            }
        });
    }

    @Override
    public int getItemCount() {
        return listings.size();
    }

    private String getDistanceFromUserToPOI(POI poi, LatLng userLocation) {
        float[] results = new float[1];
        Location.distanceBetween(userLocation.latitude, userLocation.longitude, poi.getLatitude(), poi.getLongitude(), results);
        float distanceToPOI = results[0];
        String distanceDisplayText;
        if (distanceToPOI >= maxDistanceToDisplay) {
            distanceDisplayText = "Very Far";
        } else {
            distanceDisplayText = String.valueOf((distanceToPOI))+'m';
        }
        return distanceDisplayText;
    }
}
