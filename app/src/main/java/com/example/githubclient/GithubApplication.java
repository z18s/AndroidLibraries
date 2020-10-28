package com.example.githubclient;

import android.app.Application;
import android.content.Context;

import com.example.githubclient.di.AppComponent;
import com.example.githubclient.di.DaggerAppComponent;
import com.example.githubclient.di.module.AppModule;
import com.example.githubclient.di.repository.RepositorySubcomponent;
import com.example.githubclient.di.user.UserSubcomponent;

public class GithubApplication extends Application {

    public static GithubApplication INSTANCE;

    private AppComponent appComponent;
    private UserSubcomponent userSubcomponent;
    private RepositorySubcomponent repositorySubcomponent;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public static Context getAppContext() {
        return INSTANCE;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public UserSubcomponent createUserSubcomponent() {
        if (appComponent == null) {
            throw new IllegalStateException("AppComponent must be initialized!");
        }
        if (userSubcomponent == null) {
            this.userSubcomponent = appComponent.userSubcomponent();
        }
        return userSubcomponent;
    }

    public void releaseUserSubcomponent() {
        userSubcomponent = null;
    }

    public RepositorySubcomponent createRepositorySubcomponent() {
        if (userSubcomponent == null) {
            createUserSubcomponent();
        }
        if (repositorySubcomponent == null) {
            this.repositorySubcomponent = userSubcomponent.repositorySubcomponent();
        }
        return repositorySubcomponent;
    }

    public void releaseRepositorySubcomponent() {
        repositorySubcomponent = null;
    }
}