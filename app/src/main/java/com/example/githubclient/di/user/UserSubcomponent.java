package com.example.githubclient.di.user;

import com.example.githubclient.di.repository.RepositorySubcomponent;
import com.example.githubclient.mvp.presenter.UsersPresenter;

import dagger.Subcomponent;

@UserScope
@Subcomponent(modules = UserModule.class)
public interface UserSubcomponent {
    RepositorySubcomponent repositorySubcomponent();

    void inject(UsersPresenter usersPresenter);
}