package com.urg.partner.ui.activity.splash;

import com.urg.partner.base.MvpView;
import com.urg.partner.data.network.model.CheckVersion;

public interface SplashIView extends MvpView {

    void verifyAppInstalled();

    void onSuccess(Object user);

    void onSuccess(CheckVersion user);

    void onError(Throwable e);

    void onCheckVersionError(Throwable e);
}
