package com.urg.partner.ui.activity.request_money;

import com.urg.partner.base.MvpView;
import com.urg.partner.data.network.model.RequestDataResponse;

public interface RequestMoneyIView extends MvpView {

    void onSuccess(RequestDataResponse response);
    void onSuccess(Object response);
    void onError(Throwable e);

}
