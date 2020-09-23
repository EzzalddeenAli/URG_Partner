package com.urg.partner.ui.activity.notification_manager;

import com.urg.partner.base.BasePresenter;
import com.urg.partner.data.network.APIClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NotificationManagerPresenter<V extends NotificationManagerIView> extends BasePresenter<V> implements NotificationManagerIPresenter<V> {

    @Override
    public void getNotificationManager() {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .getNotificationManager()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(getMvpView()::onSuccess, getMvpView()::onError));
    }
}
