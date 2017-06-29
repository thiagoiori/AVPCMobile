package com.avpc.avpcmobile.data.member;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.avpc.avpcmobile.data.member.IMemberRepository;
import com.avpc.avpcmobile.db.DatabaseHelper;
import com.avpc.model.Member;
import com.avpc.model.db.MemberDatabaseContract;

public class MemberRepository implements IMemberRepository {
    private SQLiteDatabase dataBaseObject;
    private Context mContext;

    public MemberRepository(Context context) {
        mContext = context;
        dataBaseObject = DatabaseHelper.getInstance(mContext).getWritableDatabase();
    }

    @Override
    public Cursor getMembers() {

        dataBaseObject = DatabaseHelper.getInstance(mContext).getReadableDatabase();
        Cursor cursorReturn = dataBaseObject.query(MemberDatabaseContract.TABLE_NAME,
                null, null, null, null, null, null, null);
        return cursorReturn;
    }

    @Override
    public Member getMember(long memberId) {
        String stringMemberId = String.valueOf(memberId);
        Member member = null;
        dataBaseObject = DatabaseHelper.getInstance(mContext).getReadableDatabase();
        Cursor cursorReturn = dataBaseObject.query(MemberDatabaseContract.TABLE_NAME,
                null, "id = ?", new String[] {stringMemberId}, null, null, null, null);

        if (cursorReturn.moveToFirst()) {
            member = loadMemberFieldsFromCursor(cursorReturn);
        }

        return member;
    }

    private Member loadMemberFieldsFromCursor(Cursor cursorReturn) {
        Member member = new Member();
        member.setId(cursorReturn.getLong(cursorReturn.getColumnIndex(MemberDatabaseContract.COLUMN_SERVER_ID)));
        return  member;
    }

    @Override
    public boolean addMember(Member member) {
        dataBaseObject.beginTransaction();
        ContentValues contentValues = new ContentValues();
        try {
            dataBaseObject.insertOrThrow(MemberDatabaseContract.TABLE_NAME,
                    null,
                    contentValues);
            dataBaseObject.setTransactionSuccessful();
        } catch(SQLException sqlEx) {
            dataBaseObject.endTransaction();
        }
        return false;
    }

    @Override
    public boolean insertMembers(Member[] members) {
        boolean inserterd = false;
        dataBaseObject.beginTransaction();
        ContentValues contentValues;
        try {
            for (int i=0;i < members.length;i++) {
                contentValues = loadMemberFields(members[i]);
                dataBaseObject.insert(MemberDatabaseContract.TABLE_NAME,
                        null,
                        contentValues);
            }

            dataBaseObject.setTransactionSuccessful();
            inserterd = true;
        } catch(SQLException sqlEx) {
            Log.e("insertMembersError: ", sqlEx.getMessage());
            inserterd = false;
        } finally {
            dataBaseObject.endTransaction();
            dataBaseObject.close();
        }
        return inserterd;
    }

    private ContentValues loadMemberFields(Member member) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", member.getId());
        contentValues.put("dni", member.getDni());
        contentValues.put("name", member.getName());
        contentValues.put("surname1", member.getSurname1());
        contentValues.put("surname2", member.getSurname2());
        contentValues.put("mobilePhoneNumber", member.getMobilePhoneNumber());
        contentValues.put("landPhoneNumber", member.getLandPhoneNumber());
        contentValues.put("email", member.getEmail());
        contentValues.put("address", member.getAddress());
        contentValues.put("city", member.getCity());
        contentValues.put("postalCode", member.getPostalCode());
        contentValues.put("userGroup", member.getUserGroup());
        contentValues.put("registryDate", member.getRegistryDate());
        contentValues.put("deletionDate", member.getDeletionDate());
        contentValues.put("lastAccesDate", member.getLastAccesDate());
        contentValues.put("birthDate", member.getBirthDate());
        contentValues.put("longitude", member.getLongitude());
        contentValues.put("latitude", member.getLatitude());
        contentValues.put("availability", member.getAvailability());
        contentValues.put("photoURL", member.getPhotoURL());
        contentValues.put("services", member.getServices());

        return contentValues;
    }
}
