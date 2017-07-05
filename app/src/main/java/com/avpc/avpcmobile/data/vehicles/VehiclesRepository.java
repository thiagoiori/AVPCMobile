package com.avpc.avpcmobile.data.vehicles;


import android.support.annotation.NonNull;

import com.avpc.model.Vehicle;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;


public class VehiclesRepository implements VehiclesDataSource {

    private static VehiclesRepository INSTANCE = null;
    private final VehiclesDataSource mVehiclesRemoteDataSource;
    private final VehiclesDataSource mVehiclesLocalDataSource;
    private List<VehiclesRepositoryObserver> mObservers = new ArrayList<>();

    private Map<Long, Vehicle> mCachedVehicles;
    private boolean mCacheIsDirty = false;

    private VehiclesRepository(@NonNull VehiclesDataSource vehiclesRemoteDataSource,
                               @NonNull VehiclesDataSource vehiclesLocalDataSource) {
        mVehiclesRemoteDataSource = checkNotNull(vehiclesRemoteDataSource);
        mVehiclesLocalDataSource = checkNotNull(vehiclesLocalDataSource);
    }

    public static VehiclesRepository getInstance(VehiclesDataSource vehiclesRemoteDataSource,
                                                 VehiclesDataSource vehiclesLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new VehiclesRepository(vehiclesRemoteDataSource, vehiclesLocalDataSource);
        }
        return INSTANCE;
    }

    public void addContentObserver(VehiclesRepositoryObserver observer) {
        if (!mObservers.contains(observer)) {
            mObservers.add(observer);
        }
    }

    public void removeContentObserver(VehiclesRepositoryObserver observer) {
        if (mObservers.contains(observer)) {
            mObservers.remove(observer);
        }
    }

    private void notifyContentObserver() {
        for (VehiclesRepositoryObserver observer : mObservers) {
            observer.onVehiclesChanged();
        }
    }

    public boolean cachedVehiclesAvailable() {
        return mCachedVehicles != null && !mCacheIsDirty;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public List<Vehicle> getVehicles() {

        List<Vehicle> vehicles = null;

        if (!mCacheIsDirty) {
            vehicles = getCachedVehicles();
        } else {
            vehicles = mVehiclesLocalDataSource.getVehicles();
        }

        if (vehicles == null || vehicles.isEmpty()) {
            vehicles = mVehiclesRemoteDataSource.getVehicles();
            saveVehiclesInLocalDataSource(vehicles);
        }

        processLoadedVehicles(vehicles);
        vehicles = getCachedVehicles();
        return vehicles;
    }

    private void processLoadedVehicles(List<Vehicle> vehicles) {

        if (vehicles == null) {
            mCachedVehicles = null;
            mCacheIsDirty = false;
            return;
        }
        if (mCachedVehicles == null) {
            mCachedVehicles = new LinkedHashMap<>();
        }
        mCachedVehicles.clear();
        for (Vehicle vehicle : vehicles) {
            mCachedVehicles.put(vehicle.getId(), vehicle);
        }
        mCacheIsDirty = false;
    }

    private void saveVehiclesInLocalDataSource(List<Vehicle> vehicles) {
        if (vehicles != null){
            for (Vehicle vehicle : vehicles) {
                mVehiclesLocalDataSource.saveVehicle(vehicle);
            }
        }
    }

    public List<Vehicle> getCachedVehicles() {
        return mCachedVehicles == null ? null : new ArrayList<>(mCachedVehicles.values());
    }

    @Override
    public void getVehicle(@NonNull String vehicleId, @NonNull GetVehicleCallback callback) {

    }

    @Override
    public void saveVehicles(@NonNull List<Vehicle> vehicles) {

    }

    @Override
    public void saveVehicle(@NonNull Vehicle vehicle) {

    }

    @Override
    public void completeVehicle(@NonNull Vehicle vehicle) {

    }

    @Override
    public void completeVehicle(@NonNull String vehicleId) {

    }

    @Override
    public void refreshVehicles() {
        mCacheIsDirty = true;
        notifyContentObserver();
    }

    @Override
    public void deleteAllVehicles() {

    }

    @Override
    public void deleteVehicle(@NonNull String vehicleId) {

    }

//    private void getVehiclesFromRemoteDataSource(@NonNull final LoadVehiclesCallback callback) {
//        mVehiclesLocalDataSource.getVehicles(new LoadVehiclesCallback() {
//            @Override
//            public void onVehiclesLoaded(List<Vehicle> vehicles) {
//                refreshCache(vehicles);
//                refreshLocalDataSource(vehicles);
//                callback.onVehiclesLoaded(new ArrayList<>(mCachedVehicles.values()));
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//                callback.onDataNotAvailable();
//            }
//        });
//    }

    private void refreshCache(List<Vehicle> vehicles) {
        if (mCachedVehicles == null) {
            mCachedVehicles = new LinkedHashMap<>();
        }
        mCachedVehicles.clear();
        for (Vehicle vehicle : vehicles) {
            mCachedVehicles.put(vehicle.getId(), vehicle);
        }
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            mVehiclesLocalDataSource.saveVehicle(vehicle);
        }
    }

    public interface VehiclesRepositoryObserver {
        void onVehiclesChanged();
    }
}
