package com.urg.partner.ui.activity.help;

import com.urg.partner.base.BasePresenter;
import com.urg.partner.data.network.APIClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HelpPresenter<V extends HelpIView> extends BasePresenter<V> implements HelpIPresenter<V> {
    @Override
    public void getHelp() {
        getCompositeDisposable().add(
                APIClient
                        .getAPIClient()
                        .getHelp()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                trendsResponse -> getMvpView().onSuccess(trendsResponse),
                                throwable -> getMvpView().onError(throwable)
                        )
        );
    }
}
