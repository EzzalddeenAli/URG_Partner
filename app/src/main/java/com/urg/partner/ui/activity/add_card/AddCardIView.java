package com.urg.partner.ui.activity.add_card;

import com.urg.partner.base.MvpView;

public interface AddCardIView extends MvpView {

    void onSuccess(Object card);

    void onError(Throwable e);
}
