package com.example.findmy.ui.profile;

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

import com.example.findmy.R;
import com.example.findmy.model.POI;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class MyPOIListAdapter extends RecyclerView.Adapter<MyPOIListAdapter.ViewHolder> {
    LatLng userLocation;
    private List<POI> myPOIList;

    private FragmentActivity parentActivity;
    private static final float maxDistanceToDisplay = (float) 2000.0;

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

    public MyPOIListAdapter(FragmentActivity parentActivity, List<POI> myPOIList, LatLng userLocation) {
        this.myPOIList = myPOIList;
        this.userLocation = userLocation;
        this.parentActivity = parentActivity;
    }

    @NonNull
    @Override
    public MyPOIListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View profileView = layoutInflater.inflate(R.layout.mypoi_list_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(profileView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyPOIListAdapter.ViewHolder holder, int position) {
        POI poi = myPOIList.get(position);

        TextView nameText = holder.nameText;
        nameText.setText(poi.getDescription());

        TextView distanceText = holder.distanceText;
        distanceText.setText(getDistanceFromUserToPOI(poi, this.userLocation));

        Button viewDetailsButton = holder.viewDetailsButton;
        viewDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPOIBottomSheet bottomSheet = new MyPOIBottomSheet(poi);
                bottomSheet.show(parentActivity.getSupportFragmentManager(), "TAG");
            }
        });
    }

    @Override
    public int getItemCount() {
        return myPOIList.size();
    }

    private String getDistanceFromUserToPOI(POI poi, LatLng userLocation) {
        float[] results = new float[1];
        Location.distanceBetween(userLocation.latitude, userLocation.longitude, poi.getLatitude(), poi.getLongitude(), results);
        float distanceToPOI = results[0];
        distanceToPOI = Math.round(distanceToPOI);
        String distanceDisplayText;
        if (distanceToPOI >= maxDistanceToDisplay) {
            distanceDisplayText = "Very Far";
        } else {
            distanceDisplayText = String.valueOf((distanceToPOI))+'m';
        }
        return distanceDisplayText;
    }
}
