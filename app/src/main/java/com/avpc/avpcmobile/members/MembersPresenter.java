package com.avpc.avpcmobile.members;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.avpc.avpcmobile.data.member.MembersLoader;
import com.avpc.avpcmobile.data.member.MembersRepository;
import com.avpc.model.Member;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by thiago on 29/06/17.
 */

public class MembersPresenter implements MembersContract.Presenter,
        LoaderManager.LoaderCallbacks<List<Member>> {

    private final MembersLoader mLoader;
    private final LoaderManager mLoaderManager;
    private final MembersRepository mMembersRepository;
    private MembersContract.View  mMembersView;
    private List<Member> mCurrentMembers;
    private boolean mFirstLoad;
    private final static int MEMBERS_QUERY = 1;

    public MembersPresenter(@NonNull MembersLoader loader,
                            @NonNull LoaderManager loaderManager,
                            @NonNull MembersRepository tasksRepository,
                            @NonNull MembersContract.View tasksView) {
        mLoader = checkNotNull(loader, "loader cannot be null!");
        mLoaderManager = checkNotNull(loaderManager, "loader manager cannot be null");
        mMembersRepository = checkNotNull(tasksRepository, "tasksRepository cannot be null");
        mMembersView = checkNotNull(tasksView, "tasksView cannot be null!");

        mMembersView.setPresenter(this);
    }

    @Override
    public Loader<List<Member>> onCreateLoader(int id, Bundle args) {
        mMembersView.setLoadingIndicator(true);
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Member>> loader, List<Member> data) {
        mMembersView.setLoadingIndicator(false);

        mCurrentMembers = data;
        if (mCurrentMembers == null) {
            mMembersView.showLoadingMembersError();
        } else {
            showFilteredMembers();
        }
    }

    private void showFilteredMembers() {
        processMembers(mCurrentMembers);
    }


    public void loadMembers(boolean forceUpdate) {
        if (forceUpdate || mFirstLoad) {
            mFirstLoad = false;
            mMembersRepository.refreshMembers();
        } else {
            showFilteredMembers();
        }
    }

    @Override
    public void openMemberDetail(long memberId) {
        mMembersView.showMemberDetailsUi(memberId);
    }

    private void processMembers(List<Member> members) {

        if (members == null || members.isEmpty()) {
            mMembersView.showNoMembers();
        } else {
            mMembersView.showMembers(members);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Member>> loader) {

    }

    @Override
    public void start() {
        mLoaderManager.initLoader(MEMBERS_QUERY, null, this);
    }
}
