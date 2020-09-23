package com.urg.partner.ui.fragment.past;


import com.urg.partner.base.MvpPresenter;

public interface PastTripIPresenter<V extends PastTripIView> extends MvpPresenter<V> {

    void getHistory();

}
