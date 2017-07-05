package com.avpc.avpcmobile.adapter.member;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avpc.avpcmobile.R;
import com.avpc.model.Member;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MemberViewHolder> {


    private static final String TAG = MemberListAdapter.class.getSimpleName();

    private List<Member> mMembersList;
    private MemberItemListener mListener;
    private Context mContext;

    public MemberListAdapter(Context context, List<Member> listMembers, MemberItemListener listener) {
        mMembersList = listMembers;
        mListener = listener;
        mContext = context;
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_members_list, parent, false);
        MemberViewHolder viewHolder = new MemberViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MemberViewHolder holder, int position) {
        Member currentObj = mMembersList.get(position);
        holder.setData(currentObj, position);
    }

    public void replaceData(List<Member> members) {
        setList(members);
        notifyDataSetChanged();
    }

    public void setList(List<Member> members) {
        mMembersList = members;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mMembersList.size();
    }

    class MemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayoutCompat layout;
        ImageView memberImage;
        TextView memberName, memberDistance, memberAvailability;
        Button btnTest;
        private int mPosition;
        private Member mMember;

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
            if (!currentObj.getPhotoURL().isEmpty()) {
                Picasso.with(mContext).load(currentObj.getPhotoURL()).into(memberImage);
            }
            memberName.setText(currentObj.getName());
            mMember = currentObj;
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            mListener.onMemberClick(mMember);
        }
    }

}
