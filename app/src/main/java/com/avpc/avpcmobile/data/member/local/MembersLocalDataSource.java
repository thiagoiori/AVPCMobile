package com.avpc.avpcmobile.data.member.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;


import com.avpc.avpcmobile.data.member.MembersDataSource;
import com.avpc.avpcmobile.db.DatabaseHelper;
import com.avpc.model.Member;
import com.avpc.model.db.MemberDatabaseContract;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


public class MembersLocalDataSource implements MembersDataSource {

    private static MembersLocalDataSource INSTANCE;
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private MembersLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new DatabaseHelper(context);
        mDb = mDbHelper.getWritableDatabase();
    }

    public static MembersLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MembersLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public List<Member> getMembers() {
        List<Member> members = null;
        Cursor cursor = mDb.query(MemberDatabaseContract.TABLE_NAME,
                null, null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            members = new ArrayList<>(cursor.getCount());
            while (cursor.moveToNext()) {
                members.add(loadMember(cursor));
            }
        }
        return members;
    }

    private Member loadMember(Cursor cursor) {
        Member member = new Member();

        try {
            member.setId(cursor.getLong(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_SERVER_ID)));
            member.setDni(cursor.getString(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_DNI)));
            member.setName(cursor.getString(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_NAME)));
            member.setSurname1(cursor.getString(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_SURNAME1)));
            member.setSurname2(cursor.getString(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_SURNAME2)));
            member.setMobilePhoneNumber(cursor.getString(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_MOBILEPHONENUMBER)));
            member.setLandPhoneNumber(cursor.getString(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_LANDPHONENUMBER)));
            member.setEmail(cursor.getString(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_EMAIL)));
            member.setAddress(cursor.getString(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_ADDRESS)));
            member.setCity(cursor.getString(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_CITY)));
            member.setPostalCode(cursor.getString(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_POSTALCODE)));
            member.setUserGroup(cursor.getString(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_USERGROUP)));
            member.setRegistryDate(cursor.getLong(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_REGISTRYDATE)));
            member.setDeletionDate(cursor.getLong(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_DELETIONDATE)));
            member.setLastAccesDate(cursor.getLong(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_LASTACCESSDATE)));
            member.setBirthDate(cursor.getLong(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_BIRTHDATE)));
            member.setLongitude(cursor.getDouble(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_LONGITUDE)));
            member.setLatitude(cursor.getDouble(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_LATITUDE)));
            member.setAvailability(cursor.getInt(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_AVAILABILITY)) == 1);
            member.setPhotoURL(cursor.getString(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_PHOTOURL)));
            member.setServices(cursor.getInt(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_SERVICES)));
        } catch (Exception e) {
            Log.e("getMember", e.getMessage());
        }

        return member;
    }

    @Override
    public Member getMember(@NonNull long memberId) {
        String selection = MemberDatabaseContract.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(memberId)};
        try {
            Cursor c = mDb.query(
                    MemberDatabaseContract.TABLE_NAME, null, selection, selectionArgs, null, null, null);

            Member member = null;

            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                member = loadMember(c);
            }

            if (c != null) {
                c.close();
            }

            return member;
        } catch (
                IllegalStateException e)

        {
            // Send to analytics, log etc
        }
        return null;
    }

    @Override
    public void saveMembers(@NonNull List<Member> members) {

    }

    @Override
    public void saveMember(@NonNull Member member) {
        ContentValues membersContent = loadMemberFields(member);
        mDb.beginTransaction();

        try {
            mDb.insert(MemberDatabaseContract.TABLE_NAME,
                    null,
                    membersContent);
            mDb.setTransactionSuccessful();
        } catch (SQLException ex) {
            Log.e("InsertMember", ex.getMessage());
        }
        finally {
            mDb.endTransaction();
        }

    }

    @Override
    public void completeMember(@NonNull Member member) {

    }

    @Override
    public void completeMember(@NonNull String memberId) {

    }

    @Override
    public void refreshMembers() {

    }

    @Override
    public void deleteAllMembers() {

    }

    @Override
    public void deleteMember(@NonNull String memberId) {

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
