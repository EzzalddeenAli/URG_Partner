package com.urg.partner.ui.bottomsheetdialog.invoice_flow;

import com.urg.partner.base.MvpView;

public interface InvoiceDialogIView extends MvpView {

    void onSuccess(Object object);
    void onError(Throwable e);
}
