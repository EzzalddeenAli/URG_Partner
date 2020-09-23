package com.urg.partner.ui.activity.summary;


import com.urg.partner.base.MvpPresenter;

public interface SummaryIPresenter<V extends SummaryIView> extends MvpPresenter<V> {

    void getSummary();
}
