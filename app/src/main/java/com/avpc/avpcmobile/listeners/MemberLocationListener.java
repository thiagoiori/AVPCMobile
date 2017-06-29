package com.avpc.avpcmobile.listeners;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by thiago on 27/06/17.
 */

public class MemberLocationListener implements LocationListener {
    private final String _logTag = MemberLocationListener.class.getSimpleName();

    @Override
    public void onLocationChanged(Location location) {
        String provider = location.getProvider();
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        float accuracy = location.getAccuracy();
        long time = location.getTime();

        Log.d("Provider", provider);
        Log.d("Latitude", (String.format("%.8f", latitude)));
        Log.d("Longitude", (String.format("%.8f", longitude)));
        Log.d("Accuracy", (String.format("%.8f", accuracy)));
        Log.d("time", (String.format("%d", time)));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
