package com.avpc.avpcmobile.adapter;

import android.content.Context;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avpc.avpcmobile.R;
import com.avpc.model.Member;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;


public class RecyclerViewMemberItemAdapter extends RecyclerView.Adapter<RecyclerViewMemberItemAdapter.MemberViewHolder> {


    private static final String TAG = RecyclerViewMemberItemAdapter.class.getSimpleName();

    private List<Member> mMembersList;
    private LayoutInflater mInflater;
    private Context mContext;

    public RecyclerViewMemberItemAdapter(Context context, List<Member> listMembers) {
        mMembersList = listMembers;
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_members_list, parent, false);
        MemberViewHolder viewHolder = new MemberViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MemberViewHolder holder, int position) {
        Member currentObj = mMembersList.get(position);
        holder.setData(currentObj, position);
    }

    @Override
    public int getItemCount() {
        return mMembersList.size();
    }

    class MemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayoutCompat layout;
        ImageView memberImage;
        TextView memberName, memberDistance, memberAvailability;
        private int mPosition;
        private Member mMember;

        public MemberViewHolder(View itemView) {
            super(itemView);

            layout = (LinearLayoutCompat) itemView.findViewById(R.id.members_list_line);
            memberImage = (ImageView) itemView.findViewById(R.id.members_list_image);
            memberName = (TextView) itemView.findViewById(R.id.members_list_member_name);
            memberDistance = (TextView) itemView.findViewById(R.id.members_list_distance);
            memberAvailability = (TextView) itemView.findViewById(R.id.members_list_availability);

        }

        public void setData(Member currentObj, int position) {
            Picasso.with(mContext).load(currentObj.getPhotoURL()).into(memberImage);
            memberName.setText(currentObj.getName());
            mMember = currentObj;
            mPosition = position;
        }

        @Override
        public void onClick(View v) {

        }
    }

}
