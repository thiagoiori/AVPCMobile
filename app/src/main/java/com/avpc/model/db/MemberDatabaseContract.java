package com.avpc.model.db;

import android.provider.BaseColumns;

/**
 * Created by thiago on 25/5/17.
 */

public class MemberDatabaseContract implements BaseColumns {

    public static final String TABLE_NAME = "tblMembers";
    public static final String COLUMN_ID = _ID;
    public static final String COLUMN_SERVER_ID = "id";
    public static final String COLUMN_DNI  = "dni";
    public static final String COLUMN_NAME  = "name";
    public static final String COLUMN_SURNAME1  = "surname1";
    public static final String COLUMN_SURNAME2  = "surname2";
    public static final String COLUMN_MOBILEPHONENUMBER  = "mobilePhoneNumber";
    public static final String COLUMN_LANDPHONENUMBER  = "landPhoneNumber";
    public static final String COLUMN_EMAIL  = "email";
    public static final String COLUMN_ADDRESS  = "address";
    public static final String COLUMN_CITY  = "city";
    public static final String COLUMN_POSTALCODE  = "postalCode";
    public static final String COLUMN_USERGROUP  = "userGroup";
    public static final String COLUMN_REGISTRYDATE  = "registryDate";
    public static final String COLUMN_DELETIONDATE  = "deletionDate";
    public static final String COLUMN_LASTACCESSDATE  = "lastAccesDate";
    public static final String COLUMN_BIRTHDATE  = "birthDate";
    public static final String COLUMN_LONGITUDE  = "longitude";
    public static final String COLUMN_LATITUDE  = "latitude";
    public static final String COLUMN_AVAILABILITY  = "availability";
    public static final String COLUMN_PHOTOURL  = "photoURL";
    public static final String COLUMN_SERVICES  = "services";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER, " +
            COLUMN_SERVER_ID + " INTEGER, " +
            COLUMN_DNI + " TEXT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_SURNAME1 + " TEXT, " +
            COLUMN_SURNAME2 + " TEXT, " +
            COLUMN_MOBILEPHONENUMBER + " TEXT, " +
            COLUMN_LANDPHONENUMBER + " TEXT, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_ADDRESS + " TEXT, " +
            COLUMN_CITY + " TEXT, " +
            COLUMN_POSTALCODE + " TEXT, " +
            COLUMN_USERGROUP + " TEXT, " +
            COLUMN_REGISTRYDATE + " NUMERIC, " +
            COLUMN_DELETIONDATE + " NUMERIC, " +
            COLUMN_LASTACCESSDATE + " NUMERIC, " +
            COLUMN_BIRTHDATE + " INTEGER, " +
            COLUMN_LONGITUDE + " REAL, " +
            COLUMN_LATITUDE + " REAL, " +
            COLUMN_AVAILABILITY + " NUMERIC, " +
            COLUMN_PHOTOURL + " TEXT, " +
            COLUMN_SERVICES + " INTEGER)";


    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
}
