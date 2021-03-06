package com.avpc.avpcmobile.map;

import com.avpc.avpcmobile.R;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Field;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapVoluntarios extends Fragment
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private MapView mMapView;
    private GoogleMap mGoogleMap;
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    private final int REQUEST_LOCATION = 1;
    private View mView;
    private SupportMapFragment myMapFragment;

    public MapVoluntarios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(mView == null)
            mView = inflater.inflate(R.layout.fragment_maps, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //FragmentManager fragment = getChildFragmentManager();//getActivity().getSupportFragmentManager();
        //myMapFragment = (SupportMapFragment) fragment.findFragmentById(R.id.map_fragment_incidentes);
        if (myMapFragment == null)
            myMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction =
                getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.map_fragment_incidentes, myMapFragment);
        fragmentTransaction.commit();
        myMapFragment.getMapAsync(this);
        buildGoogleApiClient();
    }

    protected synchronized void buildGoogleApiClient() {
        if (mGoogleApiClient == null)
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                setUserLocation(latLng);
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{ android.Manifest.permission.ACCESS_FINE_LOCATION },
                    REQUEST_LOCATION);
            return;

        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            LatLng latLng = new LatLng(
                    mLastLocation.getLatitude(),
                    mLastLocation.getLongitude());
            setUserLocation(latLng);
        } else {
            Toast.makeText(getActivity(), R.string.no_location_detected, Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "Permission denied to access location.", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }

    }

    private void setUserLocation(LatLng userLatLng) {
        mGoogleMap.addMarker(new MarkerOptions().position(userLatLng).title("Current location"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(userLatLng));
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
