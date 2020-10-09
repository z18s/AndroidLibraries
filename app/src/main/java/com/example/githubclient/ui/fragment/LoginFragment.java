package com.example.githubclient.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.githubclient.R;
import com.example.githubclient.mvp.presenter.LoginPresenter;
import com.example.githubclient.mvp.view.ILoginView;
import com.example.githubclient.ui.BackButtonListener;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class LoginFragment extends MvpAppCompatFragment implements ILoginView, BackButtonListener {

    private View mView;
    private String mLogin;

    @InjectPresenter
    LoginPresenter mPresenter;

    public LoginFragment(String login) {
        mLogin = login;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_login, container, false);
        init(mView);
        return mView;
    }

    private void init(View view) {
        TextView loginTextView = view.findViewById(R.id.user_login);
        loginTextView.setText(mLogin);
    }

    @Override
    public boolean backPressed() {
        return mPresenter.backPressed();
    }
}