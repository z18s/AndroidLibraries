package com.example.githubclient.mvp.model.repo.retrofit;

import com.example.githubclient.mvp.model.api.IDataSource;
import com.example.githubclient.mvp.model.entity.GithubRepository;
import com.example.githubclient.mvp.model.repo.IGithubRepositoriesRepo;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RetrofitGithubRepositoriesRepo implements IGithubRepositoriesRepo {
    final IDataSource api;

    public RetrofitGithubRepositoriesRepo(IDataSource api) {
        this.api = api;
    }

    @Override
    public Single<List<GithubRepository>> getRepositories(String url) {
        return api.getRepositories(url).subscribeOn(Schedulers.io());
    }
}