package com.example.githubclient.navigation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.githubclient.mvp.model.entity.GithubUser;
import com.example.githubclient.mvp.model.Tags;
import com.example.githubclient.ui.fragment.LoginFragment;
import com.example.githubclient.ui.fragment.UsersFragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {
    public static class UsersScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return new UsersFragment();
        }
    }

    public static class LoginScreen extends SupportAppScreen {
        private GithubUser user;

        public LoginScreen(GithubUser user) {
            this.user = user;
        }

        @Override
        public Fragment getFragment() {
            LoginFragment loginFragment = new LoginFragment();
            Bundle args = new Bundle();
            args.putParcelable(Tags.USER_TAG, user);
            loginFragment.setArguments(args);
            return loginFragment;
        }
    }
}