package com.avpc.avpcmobile.utils;

public class DistanceCalculator {

    public static double getDistanceFromLatLonInKm(
            double initialLatitude,
            double initialLongitude,
            double finalLatitude,
            double finalLongitude) {

        double earthRadius = 6371d;
        double dLat = deg2rad(finalLatitude-initialLatitude);
        double dLon = deg2rad(finalLongitude-initialLongitude);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(initialLatitude)) * Math.cos(deg2rad(finalLatitude)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distanceInKm = earthRadius * c;
        return distanceInKm;
    }

    private static Double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }
}
