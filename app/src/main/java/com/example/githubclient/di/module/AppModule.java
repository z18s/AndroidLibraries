package com.example.githubclient.di.module;

import com.example.githubclient.GithubApplication;

import dagger.Module;
import dagger.Provides;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;

@Module
public class AppModule {
    private GithubApplication app;

    public AppModule(GithubApplication app) {
        this.app = app;
    }

    @Provides
    public GithubApplication app() {
        return app;
    }

    @Provides
    public Scheduler mainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }
}