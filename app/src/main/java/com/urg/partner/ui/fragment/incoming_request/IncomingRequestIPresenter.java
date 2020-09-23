package com.urg.partner.ui.fragment.incoming_request;

import com.urg.partner.base.MvpPresenter;

public interface IncomingRequestIPresenter<V extends IncomingRequestIView> extends MvpPresenter<V> {

    void accept(Integer id);
    void cancel(Integer id);
}
