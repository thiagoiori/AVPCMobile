package com.avpc.avpcmobile.data.member;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import com.avpc.model.Member;

public class MemberLoader extends AsyncTaskLoader<Member>
implements MembersRepository.MembersRepositoryObserver{

    private MembersRepository mMembersRepository;
    private final long mMemberId;

    public MemberLoader(long memberId, Context context) {
        super(context);
        mMemberId = memberId;
        mMembersRepository = MembersRepositoryInjection.provideMembersRepository(context);
    }

    @Override
    public Member loadInBackground() {
       return mMembersRepository.getMember(mMemberId);
    }

    @Override
    protected void onStartLoading() {

        // Deliver any previously loaded data immediately if available.
        if (mMembersRepository.cachedMembersAvailable()) {
            deliverResult(mMembersRepository.getCachedMember(mMemberId));
        }

        mMembersRepository.addContentObserver(this);

        if (takeContentChanged() || !mMembersRepository.cachedMembersAvailable()) {
            forceLoad();
        }
    }

    @Override
    protected void onReset() {
        onStopLoading();
        mMembersRepository.removeContentObserver(this);
    }

    @Override
    public void onMembersChanged() {
        if (isStarted()) {
            forceLoad();
        }
    }
}
