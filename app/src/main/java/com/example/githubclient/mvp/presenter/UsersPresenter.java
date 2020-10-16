package com.example.githubclient.mvp.presenter;

import com.example.githubclient.GithubApplication;
import com.example.githubclient.Logger;
import com.example.githubclient.mvp.model.entity.GithubUser;
import com.example.githubclient.mvp.model.entity.GithubUserRepo;
import com.example.githubclient.mvp.presenter.list.IUserListPresenter;
import com.example.githubclient.mvp.view.IUserItemView;
import com.example.githubclient.mvp.view.IUsersView;
import com.example.githubclient.navigation.Screens;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class UsersPresenter extends MvpPresenter<IUsersView> {

    private static final String TAG = UsersPresenter.class.getSimpleName();

    private GithubUserRepo usersRepo = new GithubUserRepo();
    private Router router = GithubApplication.INSTANCE.getRouter();

    private class UsersListPresenter implements IUserListPresenter {
        private List<GithubUser> mUsers = new ArrayList<>();

        @Override
        public void onItemClick(IUserItemView view) {
            int index = view.getPos();

            Logger.showLog(Logger.INFO, TAG, " onItemClick " + index);

            GithubUser user = mUsers.get(index);
            router.navigateTo(new Screens.LoginScreen(user));
        }

        @Override
        public void bindView(IUserItemView view) {
            GithubUser user = mUsers.get(view.getPos());
            setData(view, user);
        }

        @Override
        public int getCount() {
            return mUsers.size();
        }
    }

    private UsersPresenter.UsersListPresenter userListPresenter = new UsersPresenter.UsersListPresenter();

    public IUserListPresenter getPresenter() {
        return userListPresenter;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().init();
        loadData();
    }

    private void loadData() {
        usersRepo.getUsers().subscribe(
                (users) -> {
                    Logger.showLog(Logger.INFO, TAG, "loadData.onNext " + users);
                    userListPresenter.mUsers.addAll(users);
                },
                (e) -> {
                    Logger.showLog(Logger.INFO, TAG, "loadData.onError " + e.getMessage());
                },
                () -> {
                    Logger.showLog(Logger.INFO, TAG, "loadData.onComplete");
                }
        );

        getViewState().updateList();
    }

    private void setData(IUserItemView view, GithubUser user) {
        user.getLogin().subscribe(new Observer<String>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Logger.showLog(Logger.INFO, TAG, "setData.onSubscribe");
            }

            @Override
            public void onNext(@NonNull String login) {
                Logger.showLog(Logger.INFO, TAG, "setData.onNext");
                view.setLogin(login);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Logger.showLog(Logger.INFO, TAG, "setData.onError");
            }

            @Override
            public void onComplete() {
                Logger.showLog(Logger.INFO, TAG, "setData.onComplete");
            }
        });
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}