package com.avpc.avpcmobile.fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.avpc.avpcmobile.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewIncident extends Fragment {

    private AppCompatImageButton cameraButton;
    private ImageView incidentImage;
    private View mView;
    private OnNewIncidentListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnNewIncidentListener) {
            mListener = (OnNewIncidentListener) context;
        }

    }

    public NewIncident() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_new_incident, container, false);
        }

        cameraButton = (AppCompatImageButton) mView.findViewById(R.id.new_incident_camera);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
        incidentImage = (ImageView) mView.findViewById(R.id.new_incident_image);

        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void openCamera() {
        mListener.showCamera();
    }

    public interface OnNewIncidentListener{
        void showCamera();
    }

    public void receivePhotoUri(Uri photoUri) {
        Picasso.with(getContext()).load(photoUri).into(incidentImage);
        //incidentImage.setImageURI(photoUri);
    }
}
