package com.urg.partner.ui.bottomsheetdialog.rating;

import com.urg.partner.base.MvpView;
import com.urg.partner.data.network.model.Rating;

public interface RatingDialogIView extends MvpView {

    void onSuccess(Rating rating);
    void onError(Throwable e);
}
