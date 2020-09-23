package com.urg.partner.ui.activity.wallet_detail;

import com.urg.partner.base.MvpView;
import com.urg.partner.data.network.model.Transaction;

import java.util.ArrayList;

public interface WalletDetailIView extends MvpView {
    void setAdapter(ArrayList<Transaction> myList);
}
