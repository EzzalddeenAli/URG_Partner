package com.urg.partner.ui.activity.past_detail;

import com.urg.partner.base.BasePresenter;
import com.urg.partner.data.network.APIClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PastTripDetailPresenter<V extends PastTripDetailIView> extends BasePresenter<V> implements PastTripDetailIPresenter<V> {
    @Override
    public void getPastTripDetail(String request_id) {

        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .getHistoryDetail(request_id)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getMvpView()::onSuccess, getMvpView()::onError));
    }
}
