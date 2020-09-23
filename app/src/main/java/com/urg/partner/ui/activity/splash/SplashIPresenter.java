package com.urg.partner.ui.activity.splash;

import com.urg.partner.base.MvpPresenter;

import java.util.HashMap;

public interface SplashIPresenter<V extends SplashIView> extends MvpPresenter<V> {

    void handlerCall();

    void getPlaces();

    void checkVersion(HashMap<String, Object> map);

}
