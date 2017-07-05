package com.avpc.avpcmobile.adapter.service;

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
import com.avpc.avpcmobile.adapter.service.ServiceItemListener;
import com.avpc.model.Service;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ServiceViewHolder> {


    private static final String TAG = ServiceListAdapter.class.getSimpleName();

    private List<Service> mServicesList;
    private ServiceItemListener mListener;
    private Context mContext;

    public ServiceListAdapter(Context context, List<Service> listServices, ServiceItemListener listener) {
        mServicesList = listServices;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_services_list, parent, false);
        ServiceViewHolder viewHolder = new ServiceViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder holder, int position) {
        Service currentObj = mServicesList.get(position);
        holder.setData(currentObj, position);
    }

    public void replaceData(List<Service> services) {
        setList(services);
        notifyDataSetChanged();
    }

    public void setList(List<Service> services) {
        mServicesList = services;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mServicesList.size();
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayoutCompat layout;
        ImageView serviceImage;
        TextView serviceName, serviceDistance, serviceAvailability;
        Button btnTest;
        private int mPosition;
        private Service mService;

        public ServiceViewHolder(View itemView) {
            super(itemView);

//            layout = (LinearLayoutCompat) itemView.findViewById(R.id.services_list_line);
//            serviceImage = (ImageView) itemView.findViewById(R.id.services_list_image);
//            serviceName = (TextView) itemView.findViewById(R.id.services_list_service_name);
//            serviceDistance = (TextView) itemView.findViewById(R.id.services_list_distance);
//            serviceAvailability = (TextView) itemView.findViewById(R.id.services_list_availability);
            itemView.setOnClickListener(this);

        }

        public void setData(Service currentObj, int position) {
//            Picasso.with(mContext).load(currentObj.getPhotoURL()).into(serviceImage);
//            serviceName.setText(currentObj.getName());
            mService = currentObj;
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            mListener.onServiceClick(mService);
        }
    }

}
