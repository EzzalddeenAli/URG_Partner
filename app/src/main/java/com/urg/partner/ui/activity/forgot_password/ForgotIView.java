package com.urg.partner.ui.activity.forgot_password;

import com.urg.partner.base.MvpView;
import com.urg.partner.data.network.model.ForgotResponse;

public interface ForgotIView extends MvpView {

    void onSuccess(ForgotResponse forgotResponse);
    void onError(Throwable e);
}
