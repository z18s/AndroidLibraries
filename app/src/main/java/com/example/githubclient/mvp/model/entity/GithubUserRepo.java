package com.example.githubclient.mvp.model.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GithubUserRepo {
    private List<GithubUser> mRepositories = new ArrayList<>(Arrays.asList(new GithubUser("login1"),
            new GithubUser("login2"),
            new GithubUser("login3"),
            new GithubUser("login4"),
            new GithubUser("login5")));

    public List<GithubUser> getUsers() {
        return Collections.unmodifiableList(mRepositories);
    }
}