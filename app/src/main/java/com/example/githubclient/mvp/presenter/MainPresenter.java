package com.example.githubclient.mvp.presenter;

import com.example.githubclient.GithubApplication;
import com.example.githubclient.mvp.view.IMainView;
import com.example.githubclient.navigation.Screens;

import javax.inject.Inject;

import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class MainPresenter extends MvpPresenter<IMainView> {

    @Inject
    Router router;

    public MainPresenter() {
        super();
        GithubApplication.INSTANCE.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        router.replaceScreen(new Screens.UsersScreen());
    }

    public void backClicked() {
        router.exit();
    }
}