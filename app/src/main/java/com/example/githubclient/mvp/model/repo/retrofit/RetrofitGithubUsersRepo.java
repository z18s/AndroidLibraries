package com.example.githubclient.mvp.model.repo.retrofit;

import com.example.githubclient.mvp.model.api.IDataSource;
import com.example.githubclient.mvp.model.entity.GithubUser;
import com.example.githubclient.mvp.model.repo.IGithubUsersRepo;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RetrofitGithubUsersRepo implements IGithubUsersRepo {
    IDataSource api;

    public RetrofitGithubUsersRepo(IDataSource api) {
        this.api = api;
    }

    @Override
    public Single<List<GithubUser>> getUsers() {
        return api.getUsers().subscribeOn(Schedulers.io());
    }
}