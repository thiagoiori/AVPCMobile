package com.avpc.avpcmobile.members;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avpc.avpcmobile.R;
import com.avpc.avpcmobile.adapter.member.MemberItemListener;
import com.avpc.avpcmobile.adapter.member.MemberListAdapter;
import com.avpc.avpcmobile.memberdetails.MemberDetailsActivity;
import com.avpc.model.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MembersFragment extends Fragment
implements MembersContract.View {

    private MembersContract.Presenter mPresenter;
    private MemberListAdapter mMemberListAdapter;
    ProgressDialog mProgressDialog;

    private RecyclerView mRecyclerView;
    private Context mContext;

    MemberItemListener mMemberListener = new MemberItemListener() {

        @Override
        public void onMemberClick(Member member) {
            mPresenter.openMemberDetail(member.getId());
        }
    };

    public MembersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mMemberListAdapter = new MemberListAdapter(getContext(), new ArrayList<Member>(0), mMemberListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_members_recycler_view, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.members_list_recycler_view);
        mRecyclerView.setAdapter(mMemberListAdapter);
        mProgressDialog = new ProgressDialog(getContext());
        configureDialogProgress();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                mContext
                ,1
                ,GridLayoutManager.VERTICAL
                ,false);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        return view;
    }

    private void configureDialogProgress() {
        mProgressDialog.setCancelable(false);
        mProgressDialog.setTitle("Loading");
    }

    public static MembersFragment newInstance() {
        MembersFragment fragment = new MembersFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
        //mRequestQueue.start();
    }

    @Override
    public void onStop() {
        //mRequestQueue.stop();
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void setPresenter(@NonNull MembersContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        showProgressDialog(active);
        mPresenter.loadMembers(false);
    }

    @Override
    public void showMembers(List<Member> members) {
        //mMemberListAdapter = new MemberListAdapter(getContext(), members, mMemberListener);
        mMemberListAdapter.replaceData(members);
        mRecyclerView.swapAdapter(mMemberListAdapter, false);
    }

    @Override
    public void showLoadingMembersError() {

    }

    @Override
    public void showNoMembers() {

    }

    @Override
    public void showMemberDetailsUi(long memberId) {
        Intent intent = new Intent(getContext(), MemberDetailsActivity.class);
        intent.putExtra(MemberDetailsActivity.EXTRA_MEMBER_ID, memberId);
        startActivity(intent);
    }

    private void showProgressDialog(boolean active) {

        if (active)
            mProgressDialog.show();
        else
            mProgressDialog.hide();
    }

}