package com.urg.partner.ui.activity.password;

import com.urg.partner.base.MvpView;
import com.urg.partner.data.network.model.ForgotResponse;
import com.urg.partner.data.network.model.User;

public interface PasswordIView extends MvpView {

    void onSuccess(ForgotResponse forgotResponse);

    void onSuccess(User object);

    void onError(Throwable e);
}
