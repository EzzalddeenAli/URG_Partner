package com.urg.partner.ui.activity.setting;

import com.urg.partner.base.MvpPresenter;

public interface SettingsIPresenter<V extends SettingsIView> extends MvpPresenter<V> {
    void changeLanguage(String languageID);
}
