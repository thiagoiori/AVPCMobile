package com.avpc.model.db;

import android.provider.BaseColumns;

/**
 private String brand;
 private Long id;
 private String credential;
 private String model;
 private String registrationNumber;
 */

public class VehicleDatabaseContract implements BaseColumns {
    public static final String TABLE_NAME = "tblVehicles";
    public static final String COLUMN_ID = _ID;
    public static final String COLUMN_SERVER_ID = "id";
    public static final String COLUMN_BRAND  = "brand";
    public static final String COLUMN_CREDENTIAL  = "credential";
    public static final String COLUMN_MODEL  = "model";
    public static final String COLUMN_REGISTRATION_NUMBER  = "registrationNumber";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER, " +
            COLUMN_SERVER_ID + " INTEGER, " +
            COLUMN_BRAND + " TEXT, " +
            COLUMN_CREDENTIAL + " TEXT, " +
            COLUMN_MODEL + " TEXT, " +
            COLUMN_REGISTRATION_NUMBER + " TEXT)";


    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
}
