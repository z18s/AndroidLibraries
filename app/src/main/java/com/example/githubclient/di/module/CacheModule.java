package com.example.githubclient.di.module;

import androidx.room.Room;

import com.example.githubclient.GithubApplication;
import com.example.githubclient.mvp.model.cache.IGithubRepositoriesCache;
import com.example.githubclient.mvp.model.cache.IGithubUsersCache;
import com.example.githubclient.mvp.model.cache.room.RoomGithubRepositoriesCache;
import com.example.githubclient.mvp.model.cache.room.RoomGithubUsersCache;
import com.example.githubclient.mvp.model.entity.room.Database;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CacheModule {

    @Singleton
    @Provides
    Database database() {
        return Room.databaseBuilder(GithubApplication.getAppContext(), Database.class, Database.DB_NAME)
                .build();
    }

    @Singleton
    @Provides
    IGithubUsersCache usersCache(Database db) {
        return new RoomGithubUsersCache(db);
    }

    @Singleton
    @Provides
    IGithubRepositoriesCache repositoriesRepo(Database db) {
        return new RoomGithubRepositoriesCache(db);
    }
}