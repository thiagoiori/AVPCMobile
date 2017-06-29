package com.avpc.avpcmobile.fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.avpc.avpcmobile.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewIncidentFragment extends Fragment {

    //private AppCompatImageButton cameraButton;
    private ImageView incidentImage;
    private View mView;
    private OnNewIncidentListener mListener;
    private EditText mTitle, mDescription;
    private String TITLE_TEXT = "_title_text";
    private String DESCRIPTION_TEXT = "_description_text";
    private String titleText, descriptionText;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnNewIncidentListener) {
            mListener = (OnNewIncidentListener) context;
        }
    }

    public NewIncidentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_new_incident, container, false);
        }

//        cameraButton = (AppCompatImageButton) mView.findViewById(R.id.new_incident_camera);
//        cameraButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openCamera();
//            }
//        });
        incidentImage = (ImageView) mView.findViewById(R.id.new_incident_image);

        mTitle = (EditText) mView.findViewById(R.id.new_incident_title);
        mDescription = (EditText) mView.findViewById(R.id.new_incident_description);

        if (savedInstanceState != null) {
            mTitle.setText(titleText);
            mDescription.setText(descriptionText);
        }

        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            titleText = savedInstanceState.getString(TITLE_TEXT);
            descriptionText = savedInstanceState.getString(DESCRIPTION_TEXT);
        }
    }

    private void openCamera() {
        mListener.showCamera();
    }

    public interface OnNewIncidentListener{
        void showCamera();
    }

    public void receivePhotoUri(Uri photoUri) {
        //Picasso.with(getContext()).load(photoUri).into(incidentImage);
        //incidentImage.setImageURI(photoUri);
        //incidentImage.setImageURI(getbitpam(photoUri));
        incidentImage.setImageBitmap(getbitpam(photoUri));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(TITLE_TEXT, titleText);
        outState.putString(DESCRIPTION_TEXT, descriptionText);

    }

    public void receivePhotoBitmap(Bitmap bitmap) {
        //Picasso.with(getContext()).load(photoUri).into(incidentImage);
        //incidentImage.setImageURI(photoUri);
        //incidentImage.setImageURI(getbitpam(photoUri));
        Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap,  500 ,400, false);
        incidentImage.setImageBitmap(bitmap2);
    }

    public Bitmap getbitpam(Uri imageUri){
        Bitmap imgthumBitmap=null;
        try
        {

            final int THUMBNAIL_SIZE = 64;

            FileInputStream fis = new FileInputStream(imageUri.getPath());
            imgthumBitmap = BitmapFactory.decodeStream(fis);

            imgthumBitmap = Bitmap.createScaledBitmap(
                    imgthumBitmap,
                    THUMBNAIL_SIZE
                    ,THUMBNAIL_SIZE
                    ,false);

            ByteArrayOutputStream bytearroutstream = new ByteArrayOutputStream();
            imgthumBitmap.compress(Bitmap.CompressFormat.JPEG, 100,bytearroutstream);


        }
        catch(Exception ex) {
            Log.d("", ex.getMessage());
        }
        return imgthumBitmap;
    }
}
