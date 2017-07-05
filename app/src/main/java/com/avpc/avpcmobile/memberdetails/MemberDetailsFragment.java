package com.avpc.avpcmobile.memberdetails;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avpc.avpcmobile.R;
import com.avpc.avpcmobile.util.CircleTransformation;
import com.squareup.picasso.Picasso;

public class MemberDetailsFragment extends Fragment
implements MemberDetailsContract.View {

    private static final String PARAM_MEMBER_ID = "paramMemberId";
    private Long mMemberId;
    private MemberDetailsContract.Presenter mPresenter;
    private ImageView mPicture;
    private TextView mName;
    private TextView mMobile;
    private TextView mLandNumber;
    private TextView mEmail;
    private TextView mAddress;
//    private TextView mCity;
    private TextView mUserGroup;
//    private TextView mRegistryDate;
    private TextView mLastAccessDate;
//    private TextView mLastLatitude;
//    private TextView mLastLongitude;
    private TextView mAvailability;

    public MemberDetailsFragment() {
        // Required empty public constructor
    }

    public static MemberDetailsFragment newInstance(long memberId) {
        MemberDetailsFragment fragment = new MemberDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_member_detail, container, false);
        if (savedInstanceState != null) {
            mMemberId = savedInstanceState.getLong(PARAM_MEMBER_ID);
        }

        instantiateViews(view);

        return view;
    }

    private void instantiateViews(View view) {
        mPicture = (ImageView) view.findViewById(R.id.member_detail_picture);
        mName = (TextView) view.findViewById(R.id.member_detail_name);
        mMobile = (TextView) view.findViewById(R.id.member_detail_mobile);
        mLandNumber = (TextView) view.findViewById(R.id.member_detail_phone);
        mEmail = (TextView) view.findViewById(R.id.member_detail_mail);
        mAddress = (TextView) view.findViewById(R.id.member_detail_address);
//        mCity = (TextView) view.findViewById(R.id.member_detail_city);
        mUserGroup = (TextView) view.findViewById(R.id.member_detail_user_group);
//        mRegistryDate = (TextView) view.findViewById(R.id.member_detail_registry_date);
        mLastAccessDate = (TextView) view.findViewById(R.id.member_detail_last_access_date);
//        mLastLatitude = (TextView) view.findViewById(R.id.member_detail_last_latitude);
//        mLastLongitude = (TextView) view.findViewById(R.id.member_detail_last_longitude);
        mAvailability = (TextView) view.findViewById(R.id.member_detail_availability);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
    }

    @Override
    public void setPresenter(MemberDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showLoadingMemberError() {

    }

    @Override
    public void showPicture(String url) {
        if (url.isEmpty()) {
            Picasso.with(getContext())
                    .load(R.drawable.genericuser)
                    .error(R.drawable.genericuser)
                    .transform(new CircleTransformation())
                    .into(mPicture);
        } else {
            Picasso.with(getContext())
                    .load(url)
                    .error(R.drawable.genericuser)
                    .transform(new CircleTransformation())
                    .into(mPicture);
        }
    }

    @Override
    public void showMemberName(String name) {
        mName.setText(name);
    }

    @Override
    public void showMobile(String mobile) {
        mMobile.setText(mobile);
    }

    @Override
    public void showPhone(String landPhone) {
        mLandNumber.setText(landPhone);
    }

    @Override
    public void showEmail(String email) {
        mEmail.setText(email);
    }

    @Override
    public void showAddress(String address) {
        mAddress.setText(address);
    }

//    @Override
//    public void showCity(String city) {
//        mCity.setText(city);
//    }

    @Override
    public void showUserGroup(String group) {
        mUserGroup.setText(group);
    }

//    @Override
//    public void showRegistryDate(String registryDate) {
//        mRegistryDate.setText(registryDate);
//    }

    @Override
    public void showLastAccessDate(String lastAccessDate) {
        mLastAccessDate.setText(lastAccessDate);
    }

//    @Override
//    public void showLastLatitude(String latitude) {
//        mLastLatitude.setText(latitude);
//    }
//
//    @Override
//    public void showLastLongitude(String longitude) {
//        mLastLongitude.setText(longitude);
//    }

    @Override
    public void showAvailability(String availability) {
        mAvailability.setText(availability);
    }

    @Override
    public void shoNoMember() {

    }
}
