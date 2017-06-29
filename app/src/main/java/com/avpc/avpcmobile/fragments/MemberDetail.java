package com.avpc.avpcmobile.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.avpc.avpcmobile.R;


public class MemberDetail extends Fragment {

    //private OnFragmentInteractionListener mListener;
    private static final String PARAM_MEMBER_ID = "paramMemberId";
    private Long mMemberId;

    public MemberDetail() {
        // Required empty public constructor
    }

    public static MemberDetail newInstance(long memberId) {
        MemberDetail fragment = new MemberDetail();
        Bundle args = new Bundle();
        args.putLong(PARAM_MEMBER_ID, memberId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PARAM_MEMBER_ID, mMemberId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mMemberId = savedInstanceState.getLong(PARAM_MEMBER_ID);
        }
        //Toast.makeText(getContext(), mMemberId.toString(), Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_member_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
//        appCompatActivity.setSupportActionBar(toolbar);
//        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        super.onViewCreated(view, savedInstanceState);
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMemberId = getArguments().getLong(PARAM_MEMBER_ID);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }
}
