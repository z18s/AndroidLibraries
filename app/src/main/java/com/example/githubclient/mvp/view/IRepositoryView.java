package com.example.githubclient.mvp.view;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;

@AddToEndSingle
public interface IRepositoryView extends MvpView {
    void init();
    void setName(String name);
    void setLanguage(String language);
}