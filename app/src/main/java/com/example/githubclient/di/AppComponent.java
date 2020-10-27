package com.example.githubclient.di;

import com.example.githubclient.di.module.ApiModule;
import com.example.githubclient.di.module.AppModule;
import com.example.githubclient.di.module.CacheModule;
import com.example.githubclient.di.module.CiceroneModule;
import com.example.githubclient.di.module.RepoModule;
import com.example.githubclient.mvp.presenter.MainPresenter;
import com.example.githubclient.mvp.presenter.UserPresenter;
import com.example.githubclient.mvp.presenter.UsersPresenter;
import com.example.githubclient.ui.MainActivity;
import com.example.githubclient.ui.fragment.RepositoryFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
                ApiModule.class,
                AppModule.class,
                CacheModule.class,
                CiceroneModule.class,
                RepoModule.class
})
public interface AppComponent {

    void inject(MainActivity mainActivity);
    void inject(MainPresenter mainPresenter);
    void inject(UsersPresenter usersPresenter);
    void inject(UserPresenter userPresenter);

    // ДЗ это убрать
    void inject(RepositoryFragment fragment);
}