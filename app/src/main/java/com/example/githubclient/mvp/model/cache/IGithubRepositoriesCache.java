package com.example.githubclient.mvp.model.cache;

import com.example.githubclient.mvp.model.entity.GithubRepository;
import com.example.githubclient.mvp.model.entity.GithubUser;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface IGithubRepositoriesCache {
    void putRepositories(GithubUser user, List<GithubRepository> repositories);
    Single<List<GithubRepository>> getRepositories(GithubUser user);
}