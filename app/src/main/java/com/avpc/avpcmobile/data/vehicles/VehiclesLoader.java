package com.avpc.avpcmobile.data.vehicles;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import com.avpc.model.Vehicle;

import java.util.List;


public class VehiclesLoader extends AsyncTaskLoader<List<Vehicle>>
        implements VehiclesRepository.VehiclesRepositoryObserver{

    private VehiclesRepository mVehiclesRepository;

    public VehiclesLoader(Context context, VehiclesRepository vehiclesRepository) {
        super(context);
        mVehiclesRepository = vehiclesRepository;
    }

    @Override
    public void deliverResult(List<Vehicle> data) {
        if (isReset()) {
            return;
        }

        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    public List<Vehicle> loadInBackground() {
        return mVehiclesRepository.getVehicles();
    }

    @Override
    protected void onStartLoading() {
        // Deliver any previously loaded data immediately if available.
        if (mVehiclesRepository.cachedVehiclesAvailable()) {
            deliverResult(mVehiclesRepository.getCachedVehicles());
        }

        // Begin monitoring the underlying data source.
        mVehiclesRepository.addContentObserver(this);

        if (takeContentChanged() || !mVehiclesRepository.cachedVehiclesAvailable()) {
            // When a change has  been delivered or the repository cache isn't available, we force
            // a load.
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onVehiclesChanged() {
        if (isStarted()) {
            forceLoad();
        }
    }
}
