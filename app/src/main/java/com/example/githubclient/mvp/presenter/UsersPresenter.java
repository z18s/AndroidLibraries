package com.example.githubclient.mvp.presenter;

import android.util.Log;

import com.example.githubclient.GithubApplication;
import com.example.githubclient.mvp.model.entity.GithubUser;
import com.example.githubclient.mvp.model.entity.GithubUserRepo;
import com.example.githubclient.mvp.presenter.list.IUserListPresenter;
import com.example.githubclient.mvp.view.IUserItemView;
import com.example.githubclient.mvp.view.IUsersView;

import java.util.ArrayList;
import java.util.List;

import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class UsersPresenter extends MvpPresenter<IUsersView> {

    private static final String TAG = UsersPresenter.class.getSimpleName();
    private static final boolean VERBOSE = true;

    private GithubUserRepo mUsersRepo = new GithubUserRepo();
    private Router mRouter = GithubApplication.INSTANCE.getRouter();

    private class UsersListPresenter implements IUserListPresenter {
        private List<GithubUser> mUsers = new ArrayList<>();

        @Override
        public void onItemClick(IUserItemView view) {
            if (VERBOSE) {
                Log.v(TAG, " onItemClick " + view.getPos());
            }
        }

        @Override
        public void bindView(IUserItemView view) {
            GithubUser user = mUsers.get(view.getPos());
            view.setLogin(user.getLogin());
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
        List<GithubUser> users = mUsersRepo.getUsers();
        mUserListPresenter.mUsers.addAll(users);
        getViewState().updateList();
    }

    public boolean backPressed() {
        mRouter.exit();
        return true;
    }
}