package com.example.githubclient.mvp.model.entity;

public class GithubUser {
    private String mLogin;

    public GithubUser(String login) {
        mLogin = login;
    }

    public String getLogin() {
        return mLogin;
    }

    public void setLogin(String login) {
        mLogin = login;
    }
}