package com.avpc.avpcmobile.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.avpc.avpcmobile.db.DatabaseOpenHelper;
import com.avpc.model.MemberDatabaseContract;

public class MemberRepository implements IMemberRepository {
    private SQLiteDatabase dataBaseObject;

    public MemberRepository(Context context) {
        dataBaseObject = DatabaseOpenHelper.getInstance(context).getWritableDatabase();
    }

    @Override
    public Cursor getMembers() {

        //dataBaseObject.delete("tblMembers", "_id = 1", null);
        //dataBaseObject.execSQL("INSERT INTO tblMembers (NAME, SURNAME1, availability, photoURL) VALUES ('Thiago', 'Iori', 1, 'https://pbs.twimg.com/profile_images/640835136/dimenor.jpg')");

        return dataBaseObject.query(MemberDatabaseContract.TABLE_NAME,
                null, null, null, null, null, null, null);
    }
}
