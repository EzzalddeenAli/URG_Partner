package com.urg.partner.ui.activity.wallet_detail;

import com.urg.partner.base.MvpPresenter;
import com.urg.partner.data.network.model.Transaction;

import java.util.ArrayList;

public interface WalletDetailIPresenter<V extends WalletDetailIView> extends MvpPresenter<V> {
    void setAdapter(ArrayList<Transaction> myList);
}
