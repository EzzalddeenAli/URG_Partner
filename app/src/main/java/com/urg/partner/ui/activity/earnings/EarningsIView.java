package com.urg.partner.ui.activity.earnings;


import com.urg.partner.base.MvpView;
import com.urg.partner.data.network.model.EarningsList;

public interface EarningsIView extends MvpView {

    void onSuccess(EarningsList earningsLists);

    void onError(Throwable e);
}
