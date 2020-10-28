package com.example.githubclient.mvp.presenter;

import androidx.annotation.NonNull;

import com.example.githubclient.GithubApplication;
import com.example.githubclient.Logger;
import com.example.githubclient.mvp.model.entity.GithubRepository;
import com.example.githubclient.mvp.view.IRepositoryView;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class RepositoryPresenter extends MvpPresenter<IRepositoryView> {

    private static final String TAG = RepositoryPresenter.class.getSimpleName();

    @Inject
    Router router;

    private final GithubRepository repository;

    public RepositoryPresenter(GithubRepository repository) {
        this.repository = repository;
        GithubApplication.INSTANCE.createRepositorySubcomponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().init();
        setData();
    }

    private void setData() {
        repository.getName().subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull String name) {
                Logger.showLog(Logger.INFO, TAG, "setData.onNext - " + name);
                getViewState().setName(name);
                getViewState().setLanguage(repository.getLanguage());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Logger.showLog(Logger.INFO, TAG, "setData.onError");
            }

            @Override
            public void onComplete() {
                Logger.showLog(Logger.INFO, TAG, "setData.onComplete");
            }
        });
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}