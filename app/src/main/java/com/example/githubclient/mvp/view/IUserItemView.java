package com.example.githubclient.mvp.view;

public interface IUserItemView extends IItemView {
    void setLogin(String text);
    void loadAvatar(String url);
}