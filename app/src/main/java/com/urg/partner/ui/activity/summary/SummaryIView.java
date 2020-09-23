package com.urg.partner.ui.activity.summary;


import com.urg.partner.base.MvpView;
import com.urg.partner.data.network.model.Summary;

public interface SummaryIView extends MvpView {

    void onSuccess(Summary object);

    void onError(Throwable e);
}
