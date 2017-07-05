package com.avpc.avpcmobile.memberdetails;

import android.icu.text.NumberFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;

import com.avpc.avpcmobile.R;
import com.avpc.avpcmobile.activities.base.BaseActivity;
import com.avpc.avpcmobile.data.member.MemberLoader;
import com.avpc.avpcmobile.data.member.MembersLoader;
import com.avpc.avpcmobile.data.member.MembersRepository;
import com.avpc.avpcmobile.data.member.MembersRepositoryInjection;
import com.avpc.avpcmobile.util.ActivityUtils;

import java.text.Format;
import java.util.Locale;

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

        Locale locale = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = getResources().getConfiguration().locale;
        }

        new MemberDetailsPresenter(
                memberId,
                membersRepository,
                memberDetailsFragment,
                new MemberLoader(memberId, this),
                getSupportLoaderManager(),
                locale);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
