package com.urg.partner.ui.activity.document;

import com.urg.partner.base.MvpView;
import com.urg.partner.data.network.model.DriverDocumentResponse;

public interface DocumentIView extends MvpView {

    void onSuccess(DriverDocumentResponse response);

    void onDocumentSuccess(DriverDocumentResponse response);

    void onError(Throwable e);

    void onSuccessLogout(Object object);

}
