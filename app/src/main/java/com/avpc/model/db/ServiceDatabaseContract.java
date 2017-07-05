package com.avpc.model.db;

import android.provider.BaseColumns;

/**
 * Created by thiago on 09/06/17.
 */

public class ServiceDatabaseContract implements BaseColumns {

    public static final String TABLE_NAME = "tblService";
    public static final String COLUMN_ID = _ID;
    public static final String COLUMN_START_DATE = "startDate";
    public static final String COLUMN_FINAL_DATE = "finalDate";
    public static final String COLUMN_COMMENTS = "comments";
    public static final String COLUMN_TYPE_OF_SERVICE = "typeOfService";
    public static final String COLUMN_LOCALIZATION = "localization";
    public static final String COLUMN_SERVICE_DESCRIPTION = "serviceDescription";
    public static final String COLUMN_MATERIAL = "material";
    public static final String COLUMN_MEMBER_ID = "memberId";


    public static final String SQL_CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER, " +
            COLUMN_START_DATE + " INTEGER, " +
            COLUMN_FINAL_DATE + " INTEGER, " +
            COLUMN_COMMENTS + " TEXT, " +
            COLUMN_TYPE_OF_SERVICE + " TEXT, " +
            COLUMN_LOCALIZATION + " TEXT, " +
            COLUMN_SERVICE_DESCRIPTION + " TEXT, " +
            COLUMN_MATERIAL + " TEXT," +
            COLUMN_MEMBER_ID + " INTEGER)";

    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;

}
