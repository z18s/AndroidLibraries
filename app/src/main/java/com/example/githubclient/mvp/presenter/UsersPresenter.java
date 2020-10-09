package com.example.githubclient.mvp.presenter;

import android.util.Log;

import com.example.githubclient.GithubApplication;
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
    private static final boolean VERBOSE = true;
    private static final boolean INFO = true;

    private GithubUserRepo mUsersRepo = new GithubUserRepo();
    private Router mRouter = GithubApplication.INSTANCE.getRouter();

    private class UsersListPresenter implements IUserListPresenter {
        private List<GithubUser> mUsers = new ArrayList<>();

        @Override
        public void onItemClick(IUserItemView view) {
            int index = view.getPos();

            if (VERBOSE) {
                Log.v(TAG, " onItemClick " + index);
            }

            GithubUser user = mUsers.get(index);
            mRouter.navigateTo(new Screens.LoginScreen(user));
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

    private UsersPresenter.UsersListPresenter mUserListPresenter = new UsersPresenter.UsersListPresenter();

    public IUserListPresenter getPresenter() {
        return mUserListPresenter;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().init();
        loadData();
    }

    private void loadData() {
        mUsersRepo.getUsers().subscribe(
                (users) -> {
                    if (INFO) {
                        Log.i(TAG, "loadData.onNext " + users);
                    }
                    mUserListPresenter.mUsers.addAll(users);
                },
                (e) -> {
                    if (INFO) {
                        Log.i(TAG, "loadData.onError " + e.getMessage());

                    }
                },
                () -> {
                    if (INFO) {
                        Log.i(TAG, "loadData.onComplete");
                    }
                }
        );

        getViewState().updateList();
    }

    private void setData(IUserItemView view, GithubUser user) {
        user.getLogin().subscribe(new Observer<String>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                if (INFO) {
                    Log.i(TAG, "setData.onSubscribe");
                }
            }

            @Override
            public void onNext(@NonNull String login) {
                if (INFO) {
                    Log.i(TAG, "setData.onNext " + login);
                }
                view.setLogin(login);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                if (INFO) {
                    Log.i(TAG, "setData.onError");
                }
            }

            @Override
            public void onComplete() {
                if (INFO) {
                    Log.i(TAG, "setData.onComplete");
                }
            }
        });
    }

    public boolean backPressed() {
        mRouter.exit();
        return true;
    }
}