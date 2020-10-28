package com.example.githubclient.di.repository;

import com.example.githubclient.mvp.presenter.RepositoryPresenter;
import com.example.githubclient.mvp.presenter.UserPresenter;

import dagger.Subcomponent;

@RepositoryScope
@Subcomponent(modules = RepositoryModule.class)
public interface RepositorySubcomponent {
    void inject(UserPresenter userPresenter);
    void inject(RepositoryPresenter repositoryPresenter);
}