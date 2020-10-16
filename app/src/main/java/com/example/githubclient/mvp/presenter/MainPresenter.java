package com.example.githubclient.mvp.presenter;

import com.example.githubclient.mvp.view.IMainView;
import com.example.githubclient.navigation.Screens;

import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class MainPresenter extends MvpPresenter<IMainView> {

    private Router router;// = GithubApplication.INSTANCE.getRouter();

    public MainPresenter(Router router) {
        super();
        this.router = router;
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