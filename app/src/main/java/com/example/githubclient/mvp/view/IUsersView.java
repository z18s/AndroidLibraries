package com.example.githubclient.mvp.view;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;

@AddToEndSingle
public interface IUsersView extends MvpView {
    void init();
    void updateList();
}