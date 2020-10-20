package com.example.githubclient.mvp.model.repo;

import com.example.githubclient.mvp.model.entity.GithubRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface IGithubRepositoriesRepo {
    Single<List<GithubRepository>> getRepositories(String url);
}