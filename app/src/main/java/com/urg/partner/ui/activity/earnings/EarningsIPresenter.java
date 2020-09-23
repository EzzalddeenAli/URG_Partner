package com.urg.partner.ui.activity.earnings;


import com.urg.partner.base.MvpPresenter;

public interface EarningsIPresenter<V extends EarningsIView> extends MvpPresenter<V> {

    void getEarnings();
}
