package com.avpc.avpcmobile.data.member;

import android.content.Context;

import com.avpc.avpcmobile.data.member.local.MembersLocalDataSource;
import com.avpc.avpcmobile.data.member.remote.MembersRemoteDataSource;

public class MembersRepositoryInjection {

    public static MembersRepository provideMembersRepository(Context context) {
        return MembersRepository.getInstance(MembersRemoteDataSource.getInstance(context),
                MembersLocalDataSource.getInstance(context));
    }
}
