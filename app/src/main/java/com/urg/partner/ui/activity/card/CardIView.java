package com.urg.partner.ui.activity.card;

import com.urg.partner.base.MvpView;
import com.urg.partner.data.network.model.Card;

import java.util.List;

public interface CardIView extends MvpView {

    void onSuccess(Object card);

    void onSuccess(List<Card> cards);

    void onError(Throwable e);

    void onSuccessChangeCard(Object card);
}
