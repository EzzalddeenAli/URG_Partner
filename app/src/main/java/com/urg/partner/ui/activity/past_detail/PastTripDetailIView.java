package com.urg.partner.ui.activity.past_detail;


import com.urg.partner.base.MvpView;
import com.urg.partner.data.network.model.HistoryDetail;

public interface PastTripDetailIView extends MvpView {

    void onSuccess(HistoryDetail historyDetail);
    void onError(Throwable e);
}
