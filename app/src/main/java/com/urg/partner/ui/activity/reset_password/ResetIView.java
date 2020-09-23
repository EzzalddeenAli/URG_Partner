package com.urg.partner.ui.activity.reset_password;

import com.urg.partner.base.MvpView;

public interface ResetIView extends MvpView {

    void onSuccess(Object object);
    void onError(Throwable e);
}
