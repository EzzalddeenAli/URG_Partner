package com.urg.partner.ui.activity.setting;

import com.urg.partner.base.MvpView;

public interface SettingsIView extends MvpView {

    void onSuccess(Object o);

    void onError(Throwable e);

}
