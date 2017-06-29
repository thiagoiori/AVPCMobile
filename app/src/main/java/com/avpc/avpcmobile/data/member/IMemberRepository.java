package com.avpc.avpcmobile.data.member;

import android.database.Cursor;

import com.avpc.model.Member;


public interface IMemberRepository {
    Cursor getMembers();
    Member getMember(long memberId);
    boolean addMember(Member member);

    boolean insertMembers(Member[] members);
}
