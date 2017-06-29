package com.avpc.avpcmobile.data.member;

import android.support.annotation.NonNull;

import com.avpc.model.Member;

import java.util.List;


public interface MembersDataSource {
    interface LoadMembersCallback {
        void onMembersLoaded(List<Member> members);
        void onDataNotAvailable();
    }

    interface GetMemberCallback {

        void onMemberLoaded(Member member);

        void onDataNotAvailable();
    }

    List<Member> getMembers();

    void getMember(@NonNull String memberId, @NonNull GetMemberCallback callback);



    void saveMember(@NonNull Member member);

    void completeMember(@NonNull Member member);

    void completeMember(@NonNull String memberId);

    void refreshMembers();

    void deleteAllMembers();

    void deleteMember(@NonNull String memberId);
}
