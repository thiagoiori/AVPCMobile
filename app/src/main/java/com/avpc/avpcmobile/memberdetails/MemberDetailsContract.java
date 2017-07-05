package com.avpc.avpcmobile.memberdetails;

import com.avpc.avpcmobile.BasePresenter;
import com.avpc.avpcmobile.BaseView;
import com.avpc.model.Member;

import java.util.List;

public interface MemberDetailsContract {

    interface View extends BaseView<MemberDetailsContract.Presenter> {
        void setLoadingIndicator(boolean active);
        void showLoadingMemberError();

        void showPicture(String url);
        void showMemberName(String name);
        void showMobile(String mobile);
        void showPhone(String landPhone);
        void showEmail(String email);
        void showAddress(String address);
        //void showCity(String city);
        void showUserGroup(String group);
//        void showRegistryDate(String registryDate);
        void showLastAccessDate(String lastAccessDate);
//        void showLastLatitude(String latitude);
//        void showLastLongitude(String longitude);
        void showAvailability(String availability);

        void shoNoMember();
    }

    interface Presenter extends BasePresenter {
    }
}
