package com.example.githubclient.di.user;

import com.example.githubclient.mvp.model.api.IDataSource;
import com.example.githubclient.mvp.model.cache.IGithubUsersCache;
import com.example.githubclient.mvp.model.cache.room.RoomGithubUsersCache;
import com.example.githubclient.mvp.model.entity.room.Database;
import com.example.githubclient.mvp.model.network.INetworkStatus;
import com.example.githubclient.mvp.model.repo.IGithubUsersRepo;
import com.example.githubclient.mvp.model.repo.retrofit.RetrofitGithubUsersRepo;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {

    @Provides
    IGithubUsersCache usersCache(Database db) {
        return new RoomGithubUsersCache(db);
    }

    @UserScope
    @Provides
    public IGithubUsersRepo usersRepo(IDataSource api, INetworkStatus status, IGithubUsersCache cache) {
        return new RetrofitGithubUsersRepo(api, status, cache);
    }
}