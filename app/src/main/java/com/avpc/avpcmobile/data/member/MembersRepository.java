package com.avpc.avpcmobile.data.member;


import static com.google.common.base.Preconditions.checkNotNull;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.avpc.model.Member;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class MembersRepository implements MembersDataSource {

    private static MembersRepository INSTANCE = null;
    private final MembersDataSource mMembersRemoteDataSource;
    private final MembersDataSource mMembersLocalDataSource;
    private List<MembersRepositoryObserver> mObservers = new ArrayList<>();

    private Map<Long, Member> mCachedMembers;
    private boolean mCacheIsDirty = false;

    private MembersRepository(@NonNull MembersDataSource membersRemoteDataSource,
                            @NonNull MembersDataSource membersLocalDataSource) {
        mMembersRemoteDataSource = checkNotNull(membersRemoteDataSource);
        mMembersLocalDataSource = checkNotNull(membersLocalDataSource);
    }

    public static MembersRepository getInstance(MembersDataSource membersRemoteDataSource,
                                                MembersDataSource membersLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new MembersRepository(membersRemoteDataSource, membersLocalDataSource);
        }
        return INSTANCE;
    }

    public void addContentObserver(MembersRepositoryObserver observer) {
        if (!mObservers.contains(observer)) {
            mObservers.add(observer);
        }
    }

    public void removeContentObserver(MembersRepositoryObserver observer) {
        if (mObservers.contains(observer)) {
            mObservers.remove(observer);
        }
    }

    private void notifyContentObserver() {
        for (MembersRepositoryObserver observer : mObservers) {
            observer.onMembersChanged();
        }
    }

    public boolean cachedMembersAvailable() {
        return mCachedMembers != null && !mCacheIsDirty;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public List<Member> getMembers() {

        List<Member> members = null;

        if (!mCacheIsDirty) {
            members = getCachedMembers();
        } else {
            members = mMembersLocalDataSource.getMembers();
        }

        if (members == null || members.isEmpty()) {
            members = mMembersRemoteDataSource.getMembers();
            saveMembersInLocalDataSource(members);
        }

        processLoadedMembers(members);
        members = getCachedMembers();
        return members;
    }

    private void processLoadedMembers(List<Member> members) {

        if (members == null) {
            mCachedMembers = null;
            mCacheIsDirty = false;
            return;
        }
        if (mCachedMembers == null) {
            mCachedMembers = new LinkedHashMap<>();
        }
        mCachedMembers.clear();
        for (Member member : members) {
            mCachedMembers.put(member.getId(), member);
        }
        mCacheIsDirty = false;
    }

    private void saveMembersInLocalDataSource(List<Member> members) {
        if (members != null){
            for (Member member : members) {
                mMembersLocalDataSource.saveMember(member);
            }
        }
    }

    public List<Member> getCachedMembers() {
        return mCachedMembers == null ? null : new ArrayList<>(mCachedMembers.values());
    }

    @Override
    public Member getMember(@NonNull long memberId) {
        checkNotNull(memberId);

        Member cachedMember = getMemberWithId(memberId);

        // Respond immediately with cache if we have one
        if (cachedMember != null) {
            return cachedMember;
        }

        Member task = mMembersLocalDataSource.getMember(memberId);
        if (task == null) {
            task = mMembersRemoteDataSource.getMember(memberId);
        }

        return task;
    }

    @Nullable
    private Member getMemberWithId(long id) {
        checkNotNull(id);
        if (mCachedMembers == null || mCachedMembers.isEmpty()) {
            return null;
        } else {
            return mCachedMembers.get(id);
        }
    }

    @Override
    public void saveMembers(@NonNull List<Member> members) {

    }

    @Override
    public void saveMember(@NonNull Member member) {

    }

    @Override
    public void completeMember(@NonNull Member member) {

    }

    @Override
    public void completeMember(@NonNull String memberId) {

    }

    @Override
    public void refreshMembers() {
        mCacheIsDirty = true;
        notifyContentObserver();
    }

    @Override
    public void deleteAllMembers() {

    }

    @Override
    public void deleteMember(@NonNull String memberId) {

    }

//    private void getMembersFromRemoteDataSource(@NonNull final LoadMembersCallback callback) {
//        mMembersLocalDataSource.getMembers(new LoadMembersCallback() {
//            @Override
//            public void onMembersLoaded(List<Member> members) {
//                refreshCache(members);
//                refreshLocalDataSource(members);
//                callback.onMembersLoaded(new ArrayList<>(mCachedMembers.values()));
//            }
//
//            @Override
//            public void onDataNotAvailable() {
//                callback.onDataNotAvailable();
//            }
//        });
//    }

    private void refreshCache(List<Member> members) {
        if (mCachedMembers == null) {
            mCachedMembers = new LinkedHashMap<>();
        }
        mCachedMembers.clear();
        for (Member member : members) {
            mCachedMembers.put(member.getId(), member);
        }
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<Member> members) {
        for (Member member : members) {
            mMembersLocalDataSource.saveMember(member);
        }
    }

    public Member getCachedMember(long memberId) {
        return mCachedMembers.get(memberId);
    }

    public interface MembersRepositoryObserver {
        void onMembersChanged();
    }
}
