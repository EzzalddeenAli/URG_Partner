package com.urg.partner.ui.bottomsheetdialog.invoice_flow;

import com.urg.partner.base.BasePresenter;
import com.urg.partner.data.network.APIClient;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InvoiceDialogPresenter<V extends InvoiceDialogIView> extends BasePresenter<V> implements InvoiceDialogIPresenter<V> {

    @Override
    public void statusUpdate(HashMap<String, Object> obj, Integer id) {
        getCompositeDisposable().add(APIClient
                .getAPIClient()
                .updateRequest(obj, id)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getMvpView()::onSuccess, getMvpView()::onError));
    }
}
