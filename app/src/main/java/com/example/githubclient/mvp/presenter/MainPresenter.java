package com.example.githubclient.mvp.presenter;

import com.example.githubclient.mvp.view.IMainView;
import com.example.githubclient.navigation.Screens;

import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class MainPresenter extends MvpPresenter<IMainView> {

    private final Router ROUTER;

    public MainPresenter(Router router) {
        super();
        ROUTER = router;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        ROUTER.replaceScreen(new Screens.UsersScreen());
    }

    public void backClicked() {
        ROUTER.exit();
    }
}