package com.example.githubclient.mvp.model.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class GithubUserRepo {
    private List<GithubUser> mRepositories = new ArrayList<>(Arrays.asList(new GithubUser("login1"),
            new GithubUser("login2"),
            new GithubUser("login3"),
            new GithubUser("login4"),
            new GithubUser("login5"),
            new GithubUser("login6"),
            new GithubUser("login7")
    ));

    public List<GithubUser> getUsers() {
        return Collections.unmodifiableList(mRepositories);
    }
}