package com.avpc.model.db;

import android.provider.BaseColumns;

/**
 * Created by thiago on 09/06/17.
 */

public class MessageMembersDatabaseContract implements BaseColumns {
    public static final String TABLE_NAME = "tblMessageMembers";
    public static final String COLUMN_ID = _ID;
    public static final String COLUMN_MESSAGE_ID = "messageId";
    public static final String COLUMN_MEMBERID = "memberId";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER, " +
            COLUMN_MESSAGE_ID + " INTEGER, " +
            COLUMN_MEMBERID + " INTEGER)";

}
