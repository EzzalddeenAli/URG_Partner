package com.urg.partner.ui.activity.instant_ride;

import com.urg.partner.base.MvpView;
import com.urg.partner.data.network.model.EstimateFare;
import com.urg.partner.data.network.model.TripResponse;

public interface InstantRideIView extends MvpView {

    void onSuccess(EstimateFare estimateFare);

    void onSuccess(TripResponse response);

    void onError(Throwable e);

}
