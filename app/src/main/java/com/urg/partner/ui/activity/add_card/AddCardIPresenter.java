package com.urg.partner.ui.activity.add_card;

import com.urg.partner.base.MvpPresenter;

public interface AddCardIPresenter<V extends AddCardIView> extends MvpPresenter<V> {

    void addCard(String stripeToken);
}
