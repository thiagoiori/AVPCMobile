package com.avpc.avpcmobile.memberdetails;

import android.os.Bundle;

import com.avpc.avpcmobile.R;
import com.avpc.avpcmobile.activities.base.BaseActivity;
import com.avpc.avpcmobile.data.member.MembersLoader;
import com.avpc.avpcmobile.data.member.MembersRepository;
import com.avpc.avpcmobile.data.member.MembersRepositoryInjection;
import com.avpc.avpcmobile.member.MembersFragment;
import com.avpc.avpcmobile.member.MembersPresenter;
import com.avpc.avpcmobile.util.ActivityUtils;

public class MemberDetailsActivity extends BaseActivity {

    public static final String EXTRA_MEMBER_ID = "MEMBER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long memberId = getIntent().getLongExtra(EXTRA_MEMBER_ID, 0);

        MemberDetailsFragment memberDetailsFragment =
                (MemberDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_content);
        if (memberDetailsFragment == null) {
            memberDetailsFragment = MemberDetailsFragment.newInstance(memberId);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), memberDetailsFragment, R.id.fragment_content);
        }
        MembersRepository membersRepository
                = MembersRepositoryInjection.provideMembersRepository(getApplicationContext());

        new MemberDetailsPresenter(
                memberId,
                membersRepository,
                memberDetailsFragment,
                new MembersLoader(this, membersRepository),
                getSupportLoaderManager());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
