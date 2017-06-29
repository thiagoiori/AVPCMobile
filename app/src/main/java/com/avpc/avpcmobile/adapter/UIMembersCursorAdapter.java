package com.avpc.avpcmobile.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.avpc.avpcmobile.R;
import com.avpc.avpcmobile.util.DistanceCalculator;
import com.avpc.model.db.MemberDatabaseContract;
import com.squareup.picasso.Picasso;

public class UIMembersCursorAdapter extends CursorAdapter {

    private double mLat, mLon;

    public UIMembersCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    public void setLatitude(double lat) {
        mLat = lat;
    }

    public void setLongitude(double lon) {
        mLon = lon;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.member_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView memberPicture = (ImageView)view.findViewById(R.id.member_list_picture);
        TextView memberName = (TextView) view.findViewById(R.id.member_list_volunteer_name);
        TextView memberAvailability = (TextView) view.findViewById(R.id.member_list_availability);
        TextView memberDistance = (TextView) view.findViewById(R.id.member_list_distance);

        String pictureURL = cursor.getString(cursor.getColumnIndexOrThrow(MemberDatabaseContract.COLUMN_PHOTOURL));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(MemberDatabaseContract.COLUMN_NAME));
        String lastName = cursor.getString(cursor.getColumnIndexOrThrow(MemberDatabaseContract.COLUMN_SURNAME1));
        String availability = cursor.getString(cursor.getColumnIndexOrThrow(MemberDatabaseContract.COLUMN_AVAILABILITY));
        double distance = DistanceCalculator.getDistanceFromLatLonInKm(
                mLat,
                mLon,
                51.5287714,
                -0.2420434);

        Picasso.with(context).load(pictureURL).into(memberPicture);
        memberName.setText(name + " " + lastName);
        memberAvailability.setText(availability);
        memberDistance.setText(String.format("%.4f Km", distance));
    }
}
