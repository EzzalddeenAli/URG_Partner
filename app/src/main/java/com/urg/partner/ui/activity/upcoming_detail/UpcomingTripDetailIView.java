package com.urg.partner.ui.activity.upcoming_detail;


import com.urg.partner.base.MvpView;
import com.urg.partner.data.network.model.HistoryDetail;

public interface UpcomingTripDetailIView extends MvpView {

    void onSuccess(HistoryDetail historyDetail);
    void onError(Throwable e);
}
