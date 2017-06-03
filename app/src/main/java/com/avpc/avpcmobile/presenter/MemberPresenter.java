package com.avpc.avpcmobile.presenter;

import android.content.Context;
import android.database.Cursor;

import com.avpc.avpcmobile.repository.IMemberRepository;
import com.avpc.avpcmobile.repository.MemberRepository;

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
}
