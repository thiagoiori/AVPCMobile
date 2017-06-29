package com.avpc.model.db;

import android.provider.BaseColumns;

/**
 * Created by thiago on 09/06/17.
 */

public class MessageDatabaseContract implements BaseColumns {

    public static final String TABLE_NAME = "tblMessages";
    public static final String COLUMN_ID = _ID;
    public static final String COLUMN_MESSAGE_BODY = "message";
    public static final String COLUMN_DESTINATION_MEMBERS = "destinationMembersId";
    public static final String COLUMN_SENDER_ID = "sendMember";
    public static final String COLUMN_DATE = "date";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER, " +
            COLUMN_MESSAGE_BODY + " TEXT, " +
            COLUMN_DESTINATION_MEMBERS + " INTEGER, " +
            COLUMN_SENDER_ID + " INTEGER, " +
            COLUMN_DATE + " NUMERIC)";

    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
}
