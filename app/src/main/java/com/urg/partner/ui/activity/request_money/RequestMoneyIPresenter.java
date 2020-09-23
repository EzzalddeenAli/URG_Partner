package com.urg.partner.ui.activity.request_money;

import com.urg.partner.base.MvpPresenter;

public interface RequestMoneyIPresenter<V extends RequestMoneyIView> extends MvpPresenter<V> {

    void getRequestedData();
    void requestMoney(Double requestedAmt);
    void removeRequestMoney(int id);

}
