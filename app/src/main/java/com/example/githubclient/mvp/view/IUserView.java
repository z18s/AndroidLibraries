package com.example.githubclient.mvp.view;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;

@AddToEndSingle
public interface IUserView extends MvpView {
    void init();
    void updateList();
    void setLogin(String login);
}