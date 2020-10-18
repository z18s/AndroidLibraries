package com.example.githubclient;

import android.app.Application;
import android.content.Context;

import com.example.githubclient.mvp.model.api.IDataSource;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class GithubApplication extends Application {
    public static GithubApplication INSTANCE;
    private Cicerone<Router> cicerone;
    private ApiHolder apiHolder;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        initCicerone();
        apiHolder = new ApiHolder();
    }

    public static Context getAppContext() {
        return INSTANCE;
    }

    private void initCicerone() {
        cicerone = Cicerone.create();
    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public Router getRouter() {
        return cicerone.getRouter();
    }

    public IDataSource getApi() {
        return apiHolder.getDataSource();
    }
}