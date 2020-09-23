package com.urg.partner.ui.fragment.dispute;

import com.urg.partner.base.MvpView;
import com.urg.partner.data.network.model.DisputeResponse;

import java.util.List;

public interface DisputeIView extends MvpView {

    void onSuccessDispute(List<DisputeResponse> responseList);

    void onSuccess(Object object);

    void onError(Throwable e);
}
