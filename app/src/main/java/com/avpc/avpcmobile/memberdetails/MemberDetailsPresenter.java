package com.avpc.avpcmobile.memberdetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.avpc.avpcmobile.data.member.MembersLoader;
import com.avpc.avpcmobile.data.member.MembersRepository;
import com.avpc.model.Member;

import static com.google.common.base.Preconditions.checkNotNull;

public class MemberDetailsPresenter implements MemberDetailsContract.Presenter,
        LoaderManager.LoaderCallbacks<Member>{

    private final long mMemberId;
    private final MembersRepository mMembersRepository;
    private final MemberDetailsContract.View mMemberDetailsView;
    private final MembersLoader mMemberLoader;
    private final LoaderManager mLoaderManager;

    public MemberDetailsPresenter(@Nullable long memberId,
                                  @NonNull MembersRepository membersRepository,
                                  @NonNull MemberDetailsContract.View memberDetailsView,
                                  @NonNull MembersLoader memberLoader,
                                  @NonNull LoaderManager loaderManager) {
        mMemberId = memberId;
        mMembersRepository = checkNotNull(membersRepository, "membersRepository cannot be null!");
        mMemberDetailsView = checkNotNull(memberDetailsView, "memberDetailsView cannot be null!");
        mMemberLoader = checkNotNull(memberLoader, "taskLoader cannot be null!");
        mLoaderManager = checkNotNull(loaderManager, "loaderManager cannot be null!");

        mMemberDetailsView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadMember(boolean forceUpdate) {

    }

    @Override
    public Loader<Member> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Member> loader, Member data) {

    }

    @Override
    public void onLoaderReset(Loader<Member> loader) {

    }
}
