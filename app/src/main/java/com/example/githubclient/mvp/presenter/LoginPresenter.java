package com.example.githubclient.mvp.presenter;

import com.example.githubclient.GithubApplication;
import com.example.githubclient.mvp.view.ILoginView;

import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class LoginPresenter extends MvpPresenter<ILoginView> {

    private Router router = GithubApplication.INSTANCE.getRouter();

    public boolean backPressed() {
        router.exit();
        return true;
    }
}