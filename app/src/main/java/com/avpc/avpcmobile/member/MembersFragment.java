package com.avpc.avpcmobile.member;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.avpc.avpcmobile.R;
import com.avpc.avpcmobile.adapter.MemberItemListener;
import com.avpc.avpcmobile.adapter.MemberListAdapter;
import com.avpc.avpcmobile.adapter.RecyclerViewMemberItemAdapter;
import com.avpc.avpcmobile.adapter.RecyclerViewMemberItemCursorAdapter;
import com.avpc.avpcmobile.data.member.IMemberRepository;
import com.avpc.avpcmobile.data.member.MemberRepository;
import com.avpc.avpcmobile.memberdetails.MemberDetailsActivity;
import com.avpc.avpcmobile.presenter.MemberPresenter;
import com.avpc.model.Member;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MembersFragment extends Fragment
implements MembersContract.View {

    private MembersContract.Presenter mPresenter;
    private MemberListAdapter mMemberListAdapter;
    private RequestQueue mRequestQueue;
    private RecyclerView mRecyclerView;
    private Context mContext;
    //private MemberItemListener mMemberListener;
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                mContext
                ,1
                ,GridLayoutManager.VERTICAL
                ,false);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        return view;
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
        mPresenter.loadMembers(false);
    }

    @Override
    public void showMembers(List<Member> members) {
        mMemberListAdapter = new MemberListAdapter(getContext(), members, mMemberListener);
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

}