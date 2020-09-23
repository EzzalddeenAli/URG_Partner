package com.urg.partner.ui.activity.change_password;

import com.urg.partner.base.MvpView;

public interface ChangePasswordIView extends MvpView {


    void onSuccess(Object object);
    void onError(Throwable e);
}
