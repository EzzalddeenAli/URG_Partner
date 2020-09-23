package com.urg.partner.ui.fragment.past;


import com.urg.partner.base.MvpView;
import com.urg.partner.data.network.model.HistoryList;

import java.util.List;

public interface PastTripIView extends MvpView {

    void onSuccess(List<HistoryList> historyList);
    void onError(Throwable e);
}
