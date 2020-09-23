package com.urg.partner.ui.activity.help;

import com.urg.partner.base.MvpView;
import com.urg.partner.data.network.model.Help;

public interface HelpIView extends MvpView {

    void onSuccess(Help object);

    void onError(Throwable e);
}
