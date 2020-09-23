package com.urg.partner.ui.activity.wallet;

import com.urg.partner.base.MvpView;
import com.urg.partner.data.network.model.WalletMoneyAddedResponse;
import com.urg.partner.data.network.model.WalletResponse;

public interface WalletIView extends MvpView {

    void onSuccess(WalletResponse response);

    void onSuccess(WalletMoneyAddedResponse response);

    void onError(Throwable e);
}
