package com.example.githubclient.mvp.presenter.list;

import com.example.githubclient.mvp.view.IItemView;

public interface IListPresenter <V extends IItemView> {
    void onItemClick(V view);
    void bindView(V view);
    int getCount();
}