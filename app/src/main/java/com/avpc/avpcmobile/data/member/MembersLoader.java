package com.avpc.avpcmobile.data.member;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import com.avpc.model.Member;

import java.util.List;

/**
 * Created by thiago on 30/06/17.
 */

public class MembersLoader extends AsyncTaskLoader<List<Member>>
        implements MembersRepository.MembersRepositoryObserver{

    private MembersRepository mMembersRepository;

    public MembersLoader(Context context, MembersRepository membersRepository) {
        super(context);
        mMembersRepository = membersRepository;
    }

    @Override
    public void deliverResult(List<Member> data) {
        if (isReset()) {
            return;
        }

        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    public List<Member> loadInBackground() {
        return mMembersRepository.getMembers();
    }

    @Override
    protected void onStartLoading() {
        // Deliver any previously loaded data immediately if available.
        if (mMembersRepository.cachedMembersAvailable()) {
            deliverResult(mMembersRepository.getCachedMembers());
        }

        // Begin monitoring the underlying data source.
        mMembersRepository.addContentObserver(this);

        if (takeContentChanged() || !mMembersRepository.cachedMembersAvailable()) {
            // When a change has  been delivered or the repository cache isn't available, we force
            // a load.
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onMembersChanged() {
        if (isStarted()) {
            forceLoad();
        }
    }
}
