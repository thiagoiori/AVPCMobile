package com.avpc.avpcmobile.data.member;

import android.content.Context;

import com.avpc.avpcmobile.data.member.local.MembersLocalDataSource;
import com.avpc.avpcmobile.data.member.remote.MembersRemoteDataSource;

/**
 * Created by thiago on 30/06/17.
 */

public class MembersRepositoryInjection {

    public static MembersRepository provideMembersRepository(Context context) {
        return MembersRepository.getInstance(MembersRemoteDataSource.getInstance(context),
                MembersLocalDataSource.getInstance(context));
    }
}
