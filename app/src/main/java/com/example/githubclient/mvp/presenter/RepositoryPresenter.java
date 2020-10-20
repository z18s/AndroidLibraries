package com.example.githubclient.mvp.presenter;

import com.example.githubclient.GithubApplication;
import com.example.githubclient.mvp.view.IRepositoryView;

import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class RepositoryPresenter extends MvpPresenter<IRepositoryView> {

    private static final String TAG = RepositoryPresenter.class.getSimpleName();

    private final Router ROUTER = GithubApplication.INSTANCE.getRouter();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().init();
    }

    public boolean backPressed() {
        ROUTER.exit();
        return true;
    }
}