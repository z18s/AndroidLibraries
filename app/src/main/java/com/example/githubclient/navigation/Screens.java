package com.example.githubclient.navigation;

import androidx.fragment.app.Fragment;

import com.example.githubclient.mvp.model.entity.GithubUser;
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
        private String mLogin;

        public LoginScreen(GithubUser user) {
            mLogin = user.getLogin();
        }

        @Override
        public Fragment getFragment() {
            return new LoginFragment(mLogin);
        }
    }
}