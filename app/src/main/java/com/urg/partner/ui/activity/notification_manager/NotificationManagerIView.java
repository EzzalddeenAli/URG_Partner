package com.urg.partner.ui.activity.notification_manager;

import com.urg.partner.base.MvpView;
import com.urg.partner.data.network.model.NotificationManager;

import java.util.List;

public interface NotificationManagerIView extends MvpView {

    void onSuccess(List<NotificationManager> managers);

    void onError(Throwable e);

}