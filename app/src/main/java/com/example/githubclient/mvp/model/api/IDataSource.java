package com.example.githubclient.mvp.model.api;

import com.example.githubclient.mvp.model.entity.GithubRepository;
import com.example.githubclient.mvp.model.entity.GithubUser;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IDataSource {
    @GET("/users")
    Single<List<GithubUser>> getUsers();

    @GET
    Single<List<GithubRepository>> getRepositories(@Url String url);
}