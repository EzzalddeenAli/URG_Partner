package com.urg.partner.ui.fragment.status_flow;

import com.urg.partner.base.MvpView;
import com.urg.partner.data.network.model.TimerResponse;

public interface StatusFlowIView extends MvpView {

    void onSuccess(Object object);

    void onWaitingTimeSuccess(TimerResponse object);

    void onError(Throwable e);
}
