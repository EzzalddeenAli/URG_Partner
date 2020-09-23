package com.urg.partner.ui.activity.profile;

import com.urg.partner.base.MvpView;
import com.urg.partner.data.network.model.SettingsResponse;
import com.urg.partner.data.network.model.UserResponse;

public interface ProfileIView extends MvpView {

    void onSuccess(UserResponse user);

    void onSuccessUpdate(UserResponse object);
    void onSuccess(SettingsResponse response);
    void onError(Throwable e);

    void onSuccessPhoneNumber(Object object);
    void onSuccess(Object verifyEmail);
    void onVerifyPhoneNumberError(Throwable e);

    void onVerifyEmailError(Throwable e);

}
