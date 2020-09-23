package com.urg.partner.ui.activity.invite_friend;

import com.urg.partner.base.MvpView;
import com.urg.partner.data.network.model.UserResponse;

public interface InviteFriendIView extends MvpView {

    void onSuccess(UserResponse response);
    void onError(Throwable e);

}
