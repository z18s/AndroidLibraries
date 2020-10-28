package com.example.githubclient.di.module;

import androidx.room.Room;

import com.example.githubclient.GithubApplication;
import com.example.githubclient.mvp.model.entity.room.Database;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Singleton
    @Provides
    Database database() {
        return Room.databaseBuilder(GithubApplication.getAppContext(), Database.class, Database.DB_NAME)
                .build();
    }
}