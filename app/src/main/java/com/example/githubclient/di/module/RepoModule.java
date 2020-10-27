package com.example.githubclient.di.module;

import com.example.githubclient.mvp.model.api.IDataSource;
import com.example.githubclient.mvp.model.cache.IGithubRepositoriesCache;
import com.example.githubclient.mvp.model.cache.IGithubUsersCache;
import com.example.githubclient.mvp.model.network.INetworkStatus;
import com.example.githubclient.mvp.model.repo.IGithubRepositoriesRepo;
import com.example.githubclient.mvp.model.repo.IGithubUsersRepo;
import com.example.githubclient.mvp.model.repo.retrofit.RetrofitGithubRepositoriesRepo;
import com.example.githubclient.mvp.model.repo.retrofit.RetrofitGithubUsersRepo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepoModule {
    @Singleton
    @Provides
    public IGithubUsersRepo usersRepo(IDataSource api, INetworkStatus status, IGithubUsersCache cache) {
        return new RetrofitGithubUsersRepo(api, status, cache);
    }

    @Singleton
    @Provides
    public IGithubRepositoriesRepo repositoriesRepo(IDataSource api, INetworkStatus networkStatus, IGithubRepositoriesCache cache) {
        return new RetrofitGithubRepositoriesRepo(api, networkStatus, cache);
    }
}