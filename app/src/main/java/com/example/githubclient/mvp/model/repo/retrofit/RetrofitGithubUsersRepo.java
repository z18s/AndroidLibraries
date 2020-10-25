package com.example.githubclient.mvp.model.repo.retrofit;

import com.example.githubclient.Logger;
import com.example.githubclient.mvp.model.api.IDataSource;
import com.example.githubclient.mvp.model.cache.IGithubUsersCache;
import com.example.githubclient.mvp.model.cache.room.RoomGithubUsersCache;
import com.example.githubclient.mvp.model.entity.GithubUser;
import com.example.githubclient.mvp.model.entity.room.Database;
import com.example.githubclient.mvp.model.network.INetworkStatus;
import com.example.githubclient.mvp.model.repo.IGithubUsersRepo;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RetrofitGithubUsersRepo implements IGithubUsersRepo {

    private static final String TAG = RetrofitGithubUsersRepo.class.getSimpleName();

    final IDataSource api;
    final INetworkStatus networkStatus;
    final Database db;
    final IGithubUsersCache usersCache;

    public RetrofitGithubUsersRepo(IDataSource api, INetworkStatus networkStatus, Database db) {
        this.api = api;
        this.networkStatus = networkStatus;
        this.db = db;
        usersCache = new RoomGithubUsersCache(db);
    }

    @Override
    public Single<List<GithubUser>> getUsers() {
        Logger.showLog(Logger.INFO, TAG, "getUsers");
        return networkStatus.isOnlineSingle().flatMap((isOnline) -> {
            if (isOnline) {
                Logger.showLog(Logger.INFO, TAG, "getUsers - isOnline");
                return api.getUsers().flatMap((users) ->
                        Single.fromCallable(() -> {
                                    usersCache.putUsers(users);
                                    return users;
                                }
                        ));
            } else {
                Logger.showLog(Logger.INFO, TAG, "getUsers - isOffline");
                return usersCache.getUsers();
            }
        }).subscribeOn(Schedulers.io());
    }
}