package com.avpc.avpcmobile.data.vehicles;

import android.content.Context;

import com.avpc.avpcmobile.data.vehicles.local.VehiclesLocalDataSource;
import com.avpc.avpcmobile.data.vehicles.remote.VehiclesRemoteDataSource;


public class VehiclesRepositoryInjection {

    public static VehiclesRepository provideVehiclesRepository(Context context) {
        return VehiclesRepository.getInstance(VehiclesRemoteDataSource.getInstance(context),
                VehiclesLocalDataSource.getInstance(context));
    }
}
