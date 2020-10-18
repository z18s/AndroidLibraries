package com.example.githubclient.mvp.model.repo;

import com.example.githubclient.mvp.model.entity.GithubUser;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface IGithubUsersRepo {
    Single<List<GithubUser>> getUsers();
}