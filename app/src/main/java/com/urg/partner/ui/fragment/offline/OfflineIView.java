package com.urg.partner.ui.fragment.offline;

import com.urg.partner.base.MvpView;

public interface OfflineIView extends MvpView {

    void onSuccess(Object object);
    void onError(Throwable e);
}
