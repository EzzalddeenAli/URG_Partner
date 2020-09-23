package com.urg.partner.ui.activity.add_card;

import com.urg.partner.base.BasePresenter;
import com.urg.partner.data.network.APIClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddCardPresenter<V extends AddCardIView> extends BasePresenter<V> implements AddCardIPresenter<V> {

    @Override
    public void addCard(String stripeToken) {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .addcard(stripeToken)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getMvpView()::onSuccess, getMvpView()::onError));
    }
}
