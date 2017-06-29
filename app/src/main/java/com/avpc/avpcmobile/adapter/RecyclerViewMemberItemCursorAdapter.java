package com.avpc.avpcmobile.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avpc.avpcmobile.R;
import com.avpc.avpcmobile.adapter.base.CursorRecyclerViewAdapter;
import com.avpc.model.Member;
import com.avpc.model.db.MemberDatabaseContract;
import com.squareup.picasso.Picasso;

/**
 * Created by thiago on 25/06/17.
 */

public class RecyclerViewMemberItemCursorAdapter
        extends CursorRecyclerViewAdapter<RecyclerViewMemberItemCursorAdapter.MemberViewHolder> {

    IMemberListener memberListener;

    public RecyclerViewMemberItemCursorAdapter(Context context, Cursor cursor, IMemberListener listener) {
        super(context, cursor);
        memberListener = listener;
    }

    @Override
    public void onBindViewHolder(MemberViewHolder viewHolder, Cursor cursor) {
        Member member = new Member();
        member.setId(cursor.getLong(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_ID)));
        member.setPhotoURL(cursor.getString(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_PHOTOURL)));
        member.setName(cursor.getString(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_NAME)));
        //member.set(cursor.getString(cursor.getColumnIndex(MemberDatabaseContract.COLUMN_PHOTOURL)));
        viewHolder.setData(member, 0);

    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_members_list, parent, false);
        RecyclerViewMemberItemCursorAdapter.MemberViewHolder viewHolder
                = new RecyclerViewMemberItemCursorAdapter.MemberViewHolder(view);
        return viewHolder;
    }

    private void itemSelected(Long memberId) {
        memberListener.itemClicked(memberId);
    }

    class MemberViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        LinearLayoutCompat layout;
        ImageView memberImage;
        TextView memberName, memberDistance, memberAvailability;
        private int mPosition;
        private Member mMember;
        private IMemberListener memberListener;

        public MemberViewHolder(View itemView) {
            super(itemView);

            layout = (LinearLayoutCompat) itemView.findViewById(R.id.members_list_line);
            memberImage = (ImageView) itemView.findViewById(R.id.members_list_image);
            memberName = (TextView) itemView.findViewById(R.id.members_list_member_name);
            memberDistance = (TextView) itemView.findViewById(R.id.members_list_distance);
            memberAvailability = (TextView) itemView.findViewById(R.id.members_list_availability);
            itemView.setOnClickListener(this);
        }

        public void setData(Member currentObj, int position) {
            Picasso.with(mContext).load(currentObj.getPhotoURL()).into(memberImage);
            memberName.setText(currentObj.getName());
            mMember = currentObj;
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            itemSelected(mMember.getId());
        }
    }

    public interface IMemberListener {
        void itemClicked(long memberId);
    }
}
