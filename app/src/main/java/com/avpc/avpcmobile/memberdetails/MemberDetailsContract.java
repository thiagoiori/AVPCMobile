package com.avpc.avpcmobile.memberdetails;

import com.avpc.avpcmobile.BasePresenter;
import com.avpc.avpcmobile.BaseView;
import com.avpc.model.Member;

import java.util.List;

public interface MemberDetailsContract {

    interface View extends BaseView<MemberDetailsContract.Presenter> {
        void setLoadingIndicator(boolean active);
        void showMember(List<Member> members);
        void showLoadingMembersError();
    }

    interface Presenter extends BasePresenter {
        void loadMember(boolean forceUpdate);
    }
}
