package com.avpc.model.db;

import android.provider.BaseColumns;

/**
 * Created by thiago on 09/06/17.
 */

public class ServiceMembersDatabaseContract implements BaseColumns {

    public static final String TABLE_NAME = "tblServiceMembers";
    public static final String COLUMN_ID = _ID;
    public static final String COLUMN_SERVICE_ID = "serviceId";
    public static final String COLUMN_MEMBER_ID = "memberId";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER, " +
            COLUMN_SERVICE_ID + " INTEGER, " +
            COLUMN_MEMBER_ID + " INTEGER)";
}
