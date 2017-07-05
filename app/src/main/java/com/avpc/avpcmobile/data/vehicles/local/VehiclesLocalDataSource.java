package com.avpc.avpcmobile.data.vehicles.local;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.avpc.avpcmobile.data.vehicles.VehiclesDataSource;
import com.avpc.avpcmobile.db.DatabaseHelper;
import com.avpc.model.Vehicle;
import com.avpc.model.db.VehicleDatabaseContract;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class VehiclesLocalDataSource implements VehiclesDataSource {

    private static VehiclesLocalDataSource INSTANCE;
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private VehiclesLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new DatabaseHelper(context);
        mDb = mDbHelper.getWritableDatabase();
    }

    public static VehiclesLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new VehiclesLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public List<Vehicle> getVehicles() {
        List<Vehicle> vehicles = null;
        Cursor cursor = mDb.query(VehicleDatabaseContract.TABLE_NAME,
                null, null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            vehicles = new ArrayList<>(cursor.getCount());
            while (cursor.moveToNext()) {
                vehicles.add(loadVehicle(cursor));
            }
        }
        return vehicles;
    }

    private Vehicle loadVehicle(Cursor cursor) {
        Vehicle vehicle = new Vehicle();

        try {
            vehicle.setId(cursor.getLong(cursor.getColumnIndex(VehicleDatabaseContract.COLUMN_SERVER_ID)));
            vehicle.setBrand(cursor.getString(cursor.getColumnIndex(VehicleDatabaseContract.COLUMN_BRAND)));
            vehicle.setCredential(cursor.getString(cursor.getColumnIndex(VehicleDatabaseContract.COLUMN_CREDENTIAL)));
            vehicle.setModel(cursor.getString(cursor.getColumnIndex(VehicleDatabaseContract.COLUMN_MODEL)));
            vehicle.setRegistrationNumber(cursor.getString(cursor.getColumnIndex(VehicleDatabaseContract.COLUMN_REGISTRATION_NUMBER)));

        } catch (Exception e) {
            Log.e("getVehicle", e.getMessage());
        }

        return vehicle;
    }

    @Override
    public void getVehicle(@NonNull String vehicleId, @NonNull VehiclesDataSource.GetVehicleCallback callback) {

    }

    @Override
    public void saveVehicles(@NonNull List<Vehicle> vehicles) {

    }

    @Override
    public void saveVehicle(@NonNull Vehicle vehicle) {
        ContentValues vehiclesContent = loadVehicleFields(vehicle);
        mDb.beginTransaction();

        try {
            mDb.insert(VehicleDatabaseContract.TABLE_NAME,
                    null,
                    vehiclesContent);
            mDb.setTransactionSuccessful();
        } catch (SQLException ex) {
            Log.e("InsertVehicle", ex.getMessage());
        }
        finally {
            mDb.endTransaction();
        }

    }

    @Override
    public void completeVehicle(@NonNull Vehicle vehicle) {

    }

    @Override
    public void completeVehicle(@NonNull String vehicleId) {

    }

    @Override
    public void refreshVehicles() {

    }

    @Override
    public void deleteAllVehicles() {

    }

    @Override
    public void deleteVehicle(@NonNull String vehicleId) {

    }

    private ContentValues loadVehicleFields(Vehicle vehicle) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", vehicle.getId());
        contentValues.put("brand", vehicle.getBrand());
        contentValues.put("credential", vehicle.getCredential());
        contentValues.put("model", vehicle.getModel());
        contentValues.put("registrationNumber", vehicle.getRegistrationNumber());


        return contentValues;
    }
}
