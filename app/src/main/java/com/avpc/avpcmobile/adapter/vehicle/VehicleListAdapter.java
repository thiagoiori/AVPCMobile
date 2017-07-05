package com.avpc.avpcmobile.adapter.vehicle;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avpc.avpcmobile.R;
import com.avpc.model.Vehicle;
import com.squareup.picasso.Picasso;

import java.util.List;


public class VehicleListAdapter extends RecyclerView.Adapter<VehicleListAdapter.VehicleViewHolder> {


    private static final String TAG = VehicleListAdapter.class.getSimpleName();

    private List<Vehicle> mVehiclesList;
    private VehicleItemListener mListener;
    private Context mContext;

    public VehicleListAdapter(Context context, List<Vehicle> listVehicles, VehicleItemListener listener) {
        mVehiclesList = listVehicles;
        mListener = listener;
        mContext = context;
    }

    @Override
    public VehicleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_vehicles_list, parent, false);
        VehicleViewHolder viewHolder = new VehicleViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VehicleViewHolder holder, int position) {
        Vehicle currentObj = mVehiclesList.get(position);
        holder.setData(currentObj, position);
    }

    public void replaceData(List<Vehicle> vehicles) {
        setList(vehicles);
        notifyDataSetChanged();
    }

    public void setList(List<Vehicle> vehicles) {
        mVehiclesList = vehicles;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mVehiclesList.size();
    }

    class VehicleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayoutCompat layout;
        ImageView vehicleImage;
        TextView vehicleName, vehicleDistance, vehicleAvailability;
        Button btnTest;
        private int mPosition;
        private Vehicle mVehicle;

        public VehicleViewHolder(View itemView) {
            super(itemView);

            layout = (LinearLayoutCompat) itemView.findViewById(R.id.vehicles_list_line);
//            vehicleImage = (ImageView) itemView.findViewById(R.id.vehicles_list_image);
//            vehicleName = (TextView) itemView.findViewById(R.id.vehicles_list_vehicle_name);
//            vehicleDistance = (TextView) itemView.findViewById(R.id.vehicles_list_distance);
//            vehicleAvailability = (TextView) itemView.findViewById(R.id.vehicles_list_availability);
            itemView.setOnClickListener(this);

        }

        public void setData(Vehicle currentObj, int position) {
//            Picasso.with(mContext).load(currentObj.getPhotoURL()).into(vehicleImage);
//            vehicleName.setText(currentObj.getName());
            mVehicle = currentObj;
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            mListener.onVehicleClick(mVehicle);
        }
    }

}
