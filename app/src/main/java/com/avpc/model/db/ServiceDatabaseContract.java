package com.avpc.model.db;

import android.provider.BaseColumns;

/**
 * Created by thiago on 09/06/17.
 */

public class ServiceDatabaseContract implements BaseColumns {

    private static final String TABLE_NAME = "tblService";
    private static final String COLUMN_ID = _ID;
    private static final String COLUMN_START_DATE = "startDate";
    private static final String COLUMN_FINAL_DATE = "finalDate";
    private static final String COLUMN_COMMENTS = "comments";
    private static final String COLUMN_TYPE_OF_SERVICE = "typeOfService";
    private static final String COLUMN_LOCALIZATION = "localization";
    private static final String COLUMN_SERVICE_DESCRIPTION = "serviceDescription";
    private static final String COLUMN_MATERIAL = "material";


    public static final String SQL_CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER, " +
            COLUMN_START_DATE + " INTEGER, " +
            COLUMN_FINAL_DATE + " INTEGER, " +
            COLUMN_COMMENTS + " TEXT, " +
            COLUMN_TYPE_OF_SERVICE + " TEXT, " +
            COLUMN_LOCALIZATION + " TEXT, " +
            COLUMN_SERVICE_DESCRIPTION + " TEXT, " +
            COLUMN_MATERIAL + " TEXT)";

    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
}
