package com.example.githubclient.mvp.model.repo.retrofit;

import com.example.githubclient.Logger;
import com.example.githubclient.mvp.model.api.IDataSource;
import com.example.githubclient.mvp.model.cache.IGithubRepositoriesCache;
import com.example.githubclient.mvp.model.cache.room.RoomGithubRepositoriesCache;
import com.example.githubclient.mvp.model.entity.GithubRepository;
import com.example.githubclient.mvp.model.entity.GithubUser;
import com.example.githubclient.mvp.model.entity.room.Database;
import com.example.githubclient.mvp.model.network.INetworkStatus;
import com.example.githubclient.mvp.model.repo.IGithubRepositoriesRepo;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RetrofitGithubRepositoriesRepo implements IGithubRepositoriesRepo {

    private static final String TAG = RetrofitGithubRepositoriesRepo.class.getSimpleName();

    final IDataSource api;
    final INetworkStatus networkStatus;
    final Database db;
    final IGithubRepositoriesCache repositoriesCache;

    public RetrofitGithubRepositoriesRepo(IDataSource api, INetworkStatus networkStatus, Database db) {
        this.api = api;
        this.networkStatus = networkStatus;
        this.db = db;
        repositoriesCache = new RoomGithubRepositoriesCache(db);
    }

    @Override
    public Single<List<GithubRepository>> getRepositories(GithubUser user) {
        Logger.showLog(Logger.INFO, TAG, "getRepositories");
        return networkStatus.isOnlineSingle().flatMap((isOnline) -> {
            if (isOnline) {
                Logger.showLog(Logger.INFO, TAG, "getRepositories - isOnline");
                return api.getRepositories(user.getRepositoriesUrl()).flatMap((repositories) ->
                        Single.fromCallable(() -> {
                                    repositoriesCache.putRepositories(user, repositories);
                                    return repositories;
                                }
                        ));
            } else {
                Logger.showLog(Logger.INFO, TAG, "getRepositories - isOffline");
                return repositoriesCache.getRepositories(user);
            }
        }).subscribeOn(Schedulers.io());
    }
}