package com.example.githubclient.mvp.presenter;

import com.example.githubclient.GithubApplication;
import com.example.githubclient.mvp.view.ILoginView;

import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class LoginPresenter extends MvpPresenter<ILoginView> {

    private Router mRouter = GithubApplication.INSTANCE.getRouter();

    public boolean backPressed() {
        mRouter.exit();
        return true;
    }
}