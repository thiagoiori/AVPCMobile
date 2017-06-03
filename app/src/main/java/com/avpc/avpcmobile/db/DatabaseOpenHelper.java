package com.avpc.avpcmobile.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.avpc.model.MemberDatabaseContract;

/**
 * Created by thiago on 25/5/17.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "dbAVPB";
    private static final int DB_VERSION = 1;

    private static DatabaseOpenHelper mInstance;

    public static synchronized DatabaseOpenHelper getInstance(Context ctx) {

        if (mInstance == null) {
            mInstance = new DatabaseOpenHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MemberDatabaseContract.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MemberDatabaseContract.SQL_DROP_TABLE);
    }
}
