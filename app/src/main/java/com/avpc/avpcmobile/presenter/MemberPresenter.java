package com.avpc.avpcmobile.presenter;

import android.content.Context;
import android.database.Cursor;

import com.avpc.avpcmobile.data.member.IMemberRepository;
import com.avpc.avpcmobile.data.member.MemberRepository;
import com.avpc.model.Member;

public class MemberPresenter {
    Context context;
    IMemberRepository repository;

    public MemberPresenter(Context context) {
        this.context = context;
        this.repository = new MemberRepository(context);
    }

    public Cursor getMembers() {
        return this.repository.getMembers();
    }

    public Member getMember(long memberId) {
        return this.repository.getMember(memberId);
    }
}
