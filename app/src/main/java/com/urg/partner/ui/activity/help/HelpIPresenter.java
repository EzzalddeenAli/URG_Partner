package com.urg.partner.ui.activity.help;


import com.urg.partner.base.MvpPresenter;

public interface HelpIPresenter<V extends HelpIView> extends MvpPresenter<V> {

    void getHelp();
}
