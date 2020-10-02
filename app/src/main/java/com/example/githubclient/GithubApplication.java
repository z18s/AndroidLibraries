package com.example.githubclient;

import android.app.Application;
import android.content.Context;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class GithubApplication extends Application {
    public static GithubApplication INSTANCE;
    private Cicerone<Router> mCicerone;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        initCicerone();
    }

    public static Context getAppContext() {
        return INSTANCE;
    }

    private void initCicerone() {
        mCicerone = Cicerone.create();
    }

    public NavigatorHolder getNavigatorHolder() {
        return mCicerone.getNavigatorHolder();
    }

    public Router getRouter() {
        return mCicerone.getRouter();
    }

}