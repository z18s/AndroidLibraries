package com.example.githubclient.navigation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.githubclient.mvp.model.Tags;
import com.example.githubclient.mvp.model.entity.GithubRepository;
import com.example.githubclient.mvp.model.entity.GithubUser;
import com.example.githubclient.ui.fragment.RepositoryFragment;
import com.example.githubclient.ui.fragment.UserFragment;
import com.example.githubclient.ui.fragment.UsersFragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {
    public static class UsersScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return new UsersFragment();
        }
    }

    public static class UserScreen extends SupportAppScreen {
        private final GithubUser user;

        public UserScreen(GithubUser user) {
            this.user = user;
        }

        @Override
        public Fragment getFragment() {
            UserFragment userFragment = new UserFragment();
            Bundle args = new Bundle();
            args.putParcelable(Tags.USER_TAG, user);
            userFragment.setArguments(args);
            return userFragment;
        }
    }

    public static class RepositoryScreen extends SupportAppScreen {
        private final GithubRepository repository;

        public RepositoryScreen(GithubRepository repository) {
            this.repository = repository;
        }

        @Override
        public Fragment getFragment() {
            RepositoryFragment repositoryFragment = new RepositoryFragment();
            Bundle args = new Bundle();
            args.putParcelable(Tags.REPOSITORY_TAG, repository);
            repositoryFragment.setArguments(args);
            return repositoryFragment;
        }
    }
}