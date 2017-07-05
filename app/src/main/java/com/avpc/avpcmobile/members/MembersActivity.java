package com.avpc.avpcmobile.members;

import android.os.Bundle;

import com.avpc.avpcmobile.R;
import com.avpc.avpcmobile.activities.base.BaseActivity;
import com.avpc.avpcmobile.data.member.MembersLoader;
import com.avpc.avpcmobile.data.member.MembersRepository;
import com.avpc.avpcmobile.data.member.MembersRepositoryInjection;
import com.avpc.avpcmobile.util.ActivityUtils;

public class MembersActivity extends BaseActivity {

    private MembersPresenter mMembersPresenter;
    private MembersLoader mLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MembersFragment membersFragment =
                (MembersFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_content);
        if (membersFragment == null) {
            // Create the fragment
            membersFragment = MembersFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), membersFragment, R.id.fragment_content);
        }
        MembersRepository membersRepository
                = MembersRepositoryInjection.provideMembersRepository(getApplicationContext());
        mLoader = new MembersLoader(this, membersRepository);

        mMembersPresenter = new MembersPresenter(
                mLoader,
                getSupportLoaderManager(),
                membersRepository,
                membersFragment);

    }
}
