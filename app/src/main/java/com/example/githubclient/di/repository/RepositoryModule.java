package com.example.githubclient.di.repository;

import com.example.githubclient.mvp.model.api.IDataSource;
import com.example.githubclient.mvp.model.cache.IGithubRepositoriesCache;
import com.example.githubclient.mvp.model.cache.room.RoomGithubRepositoriesCache;
import com.example.githubclient.mvp.model.entity.room.Database;
import com.example.githubclient.mvp.model.network.INetworkStatus;
import com.example.githubclient.mvp.model.repo.IGithubRepositoriesRepo;
import com.example.githubclient.mvp.model.repo.retrofit.RetrofitGithubRepositoriesRepo;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    IGithubRepositoriesCache repositoriesCache(Database db) {
        return new RoomGithubRepositoriesCache(db);
    }

    @RepositoryScope
    @Provides
    public IGithubRepositoriesRepo repositoriesRepo(IDataSource api, INetworkStatus networkStatus, IGithubRepositoriesCache cache) {
        return new RetrofitGithubRepositoriesRepo(api, networkStatus, cache);
    }
}