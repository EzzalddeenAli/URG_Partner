package com.urg.partner.ui.activity.change_password;

import com.urg.partner.base.MvpPresenter;

import java.util.HashMap;

public interface ChangePasswordIPresenter<V extends ChangePasswordIView> extends MvpPresenter<V> {

    void changePassword(HashMap<String, Object> obj);
}
