package com.avpc.avpcmobile.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.avpc.model.db.MemberDatabaseContract;
import com.avpc.model.db.MessageDatabaseContract;
import com.avpc.model.db.ServiceDatabaseContract;
import com.avpc.model.db.VehicleDatabaseContract;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "dbAVPC.db";
    private static final int DB_VERSION = 6;

    private static DatabaseHelper mInstance;

    public static synchronized DatabaseHelper getInstance(Context ctx) {

        if (mInstance == null) {
            mInstance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MemberDatabaseContract.SQL_CREATE_TABLE);
        db.execSQL(MessageDatabaseContract.SQL_CREATE_TABLE);
        db.execSQL(VehicleDatabaseContract.SQL_CREATE_TABLE);
        db.execSQL(ServiceDatabaseContract.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MemberDatabaseContract.SQL_DROP_TABLE);
        db.execSQL(VehicleDatabaseContract.SQL_DROP_TABLE);
        db.execSQL(MessageDatabaseContract.SQL_DROP_TABLE);
        db.execSQL(ServiceDatabaseContract.SQL_DROP_TABLE);
        onCreate(db);
    }
}
