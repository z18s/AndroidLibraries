package com.example.githubclient.di;

import com.example.githubclient.di.module.ApiModule;
import com.example.githubclient.di.module.AppModule;
import com.example.githubclient.di.module.DatabaseModule;
import com.example.githubclient.di.module.CiceroneModule;
import com.example.githubclient.di.user.UserSubcomponent;
import com.example.githubclient.mvp.presenter.MainPresenter;
import com.example.githubclient.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
                ApiModule.class,
                AppModule.class,
                DatabaseModule.class,
                CiceroneModule.class
})
public interface AppComponent {
    UserSubcomponent userSubcomponent();

    void inject(MainActivity mainActivity);
    void inject(MainPresenter mainPresenter);
}