package com.urg.partner.ui.fragment.upcoming;

import com.urg.partner.base.MvpView;
import com.urg.partner.data.network.model.HistoryList;

import java.util.List;

public interface UpcomingTripIView extends MvpView {

    void onSuccess(List<HistoryList> historyList);
    void onError(Throwable e);
}
