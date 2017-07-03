package com.avpc.avpcmobile.member;

import com.avpc.avpcmobile.BasePresenter;
import com.avpc.avpcmobile.BaseView;
import com.avpc.model.Member;

import java.util.List;

/**
 * Created by thiago on 29/06/17.
 */

public interface MembersContract {

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);
        void showMembers(List<Member> members);
        void showLoadingMembersError();
        void showNoMembers();

        void showMemberDetailsUi(long memberId);
    }

    interface Presenter extends BasePresenter {
        void loadMembers(boolean forceUpdate);

        void openMemberDetail(long memberId);
    }
}
