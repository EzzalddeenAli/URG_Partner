package com.urg.partner.ui.activity.notification_manager;

import com.urg.partner.base.MvpPresenter;

public interface NotificationManagerIPresenter<V extends NotificationManagerIView> extends MvpPresenter<V> {
    void getNotificationManager();
}
