package com.example.githubclient.mvp.model.cache;

import com.example.githubclient.mvp.model.entity.GithubUser;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface IGithubUsersCache {
    void putUsers(List<GithubUser> users);
    Single<List<GithubUser>> getUsers();
}