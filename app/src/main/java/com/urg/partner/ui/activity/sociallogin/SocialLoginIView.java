package com.urg.partner.ui.activity.sociallogin;

import com.urg.partner.base.MvpView;
import com.urg.partner.data.network.model.Token;

public interface SocialLoginIView extends MvpView {

    void onSuccess(Token token);
    void onError(Throwable e);
}
