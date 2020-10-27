package com.example.githubclient.mvp.presenter;

import androidx.annotation.UiThread;

import com.example.githubclient.GithubApplication;
import com.example.githubclient.Logger;
import com.example.githubclient.mvp.model.entity.GithubUser;
import com.example.githubclient.mvp.model.repo.IGithubUsersRepo;
import com.example.githubclient.mvp.presenter.list.IUserListPresenter;
import com.example.githubclient.mvp.view.IUserItemView;
import com.example.githubclient.mvp.view.IUsersView;
import com.example.githubclient.navigation.Screens;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class UsersPresenter extends MvpPresenter<IUsersView> {

    private static final String TAG = UsersPresenter.class.getSimpleName();

    @Inject
    IGithubUsersRepo usersRepo;
    @Inject
    Router router;
    @Inject
    Scheduler scheduler;

    public UsersPresenter() {
        GithubApplication.INSTANCE.getAppComponent().inject(this);
    }

    private class UsersListPresenter implements IUserListPresenter {
        private final List<GithubUser> users = new ArrayList<>();

        @Override
        public void onItemClick(IUserItemView view) {
            int index = view.getPos();

            Logger.showLog(Logger.INFO, TAG, "onItemClick " + index);

            GithubUser user = users.get(index);
            router.navigateTo(new Screens.UserScreen(user));
        }

        @Override
        public void bindView(IUserItemView view) {
            GithubUser user = users.get(view.getPos());
            setRecyclerData(view, user);
        }

        @Override
        public int getCount() {
            return users.size();
        }
    }

    private final UsersPresenter.UsersListPresenter userListPresenter = new UsersPresenter.UsersListPresenter();

    public IUserListPresenter getPresenter() {
        return userListPresenter;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().init();
        setData();
    }

    @UiThread
    private void setData() {
        usersRepo.getUsers().observeOn(scheduler).subscribe(
                (users) -> {
                    Logger.showLog(Logger.INFO, TAG, "setData.onNext " + users);
                    userListPresenter.users.clear();
                    userListPresenter.users.addAll(users);
                    getViewState().updateList();
                },
                (e) -> {
                    Logger.showLog(Logger.INFO, TAG, "setData.onError " + e.getMessage());
                }
        );
        getViewState().updateList();
    }

    private void setRecyclerData(IUserItemView view, GithubUser user) {
        user.getLogin().subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull String login) {
                view.setLogin(login);
                view.loadAvatar(user.getAvatarUrl());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Logger.showLog(Logger.INFO, TAG, "setRecyclerData.onError");
            }

            @Override
            public void onComplete() {
                Logger.showLog(Logger.INFO, TAG, "setRecyclerData.onComplete");
            }
        });
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}