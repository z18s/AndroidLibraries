package com.example.githubclient;

import android.app.Application;
import android.content.Context;

import com.example.githubclient.di.AppComponent;
import com.example.githubclient.di.DaggerAppComponent;
import com.example.githubclient.di.module.AppModule;

public class GithubApplication extends Application {

    public static GithubApplication INSTANCE;
    private AppComponent appComponent;

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
}