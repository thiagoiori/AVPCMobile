package com.avpc.avpcmobile.memberdetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.avpc.avpcmobile.data.member.MemberLoader;
import com.avpc.avpcmobile.data.member.MembersLoader;
import com.avpc.avpcmobile.data.member.MembersRepository;
import com.avpc.avpcmobile.util.DateTimeFormatter;
import com.avpc.model.Member;
import java.util.Locale;

import static com.google.common.base.Preconditions.checkNotNull;

public class MemberDetailsPresenter implements MemberDetailsContract.Presenter,
        LoaderManager.LoaderCallbacks<Member>{

    private final long mMemberId;
    private final MembersRepository mMembersRepository;
    private final MemberDetailsContract.View mMemberDetailsView;
    private final MemberLoader mMemberLoader;
    private final LoaderManager mLoaderManager;
    private final Locale mLocale;
    private static final int MEMBER_QUERY = 10;

    public MemberDetailsPresenter(@Nullable long memberId,
                                  @NonNull MembersRepository membersRepository,
                                  @NonNull MemberDetailsContract.View memberDetailsView,
                                  @NonNull MemberLoader memberLoader,
                                  @NonNull LoaderManager loaderManager,
                                  @NonNull Locale locale) {
        mMemberId = memberId;
        mMembersRepository = checkNotNull(membersRepository, "membersRepository cannot be null!");
        mMemberDetailsView = checkNotNull(memberDetailsView, "memberDetailsView cannot be null!");
        mMemberLoader = checkNotNull(memberLoader, "taskLoader cannot be null!");
        mLoaderManager = checkNotNull(loaderManager, "loaderManager cannot be null!");
        mLocale = checkNotNull(locale, "locale cannot be null!");

        mMemberDetailsView.setPresenter(this);
    }

    @Override
    public void start() {
        mLoaderManager.initLoader(MEMBER_QUERY, null, this);
    }

    private void showMember(Member member) {

        mMemberDetailsView.showPicture(member.getPhotoURL());
        mMemberDetailsView.showMemberName(String.format("%s %s", member.getName(), member.getSurname1()));
        mMemberDetailsView.showMobile(member.getMobilePhoneNumber().isEmpty() ? "Mobile not available" : member.getMobilePhoneNumber());
        mMemberDetailsView.showPhone(member.getLandPhoneNumber().isEmpty() ? "Fixed Phone not available" : member.getLandPhoneNumber());
        mMemberDetailsView.showEmail(member.getEmail().isEmpty() ? "E-mail not available" : member.getEmail());
        mMemberDetailsView.showAddress(String.format("%s, %s", member.getAddress(), member.getCity()));
        mMemberDetailsView.showUserGroup(member.getUserGroup().isEmpty() ? "Group not available" : member.getUserGroup());
//        mMemberDetailsView.showRegistryDate(DateTimeFormatter.formatDate(member.getRegistryDate()));
        mMemberDetailsView.showLastAccessDate(member.getLastAccesDate() == 0 ? "Last access not available": DateTimeFormatter.formatDate(member.getLastAccesDate()));
//        mMemberDetailsView.showLastLatitude(String.format(mLocale, "%.10f", member.getLatitude()));
//        mMemberDetailsView.showLastLongitude(String.format(mLocale, "%.10f", member.getLatitude()));
        mMemberDetailsView.showAvailability(member.getAvailability() ? "Online" : "Offline");
    }

    @Override
    public Loader<Member> onCreateLoader(int id, Bundle args) {
        mMemberDetailsView.setLoadingIndicator(true);
        return mMemberLoader;
    }

    @Override
    public void onLoadFinished(Loader<Member> loader, Member data) {
        if (data != null) {
            showMember(data);
        } else {
            mMemberDetailsView.shoNoMember();
        }
    }

    @Override
    public void onLoaderReset(Loader<Member> loader) {

    }
}
