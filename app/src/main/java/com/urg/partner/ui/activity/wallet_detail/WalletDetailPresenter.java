package com.urg.partner.ui.activity.wallet_detail;

import com.urg.partner.base.BasePresenter;
import com.urg.partner.data.network.model.Transaction;

import java.util.ArrayList;

public class WalletDetailPresenter<V extends WalletDetailIView> extends BasePresenter<V> implements WalletDetailIPresenter<V> {
    @Override
    public void setAdapter(ArrayList<Transaction> myList) {
        getMvpView().setAdapter(myList);
    }
}
