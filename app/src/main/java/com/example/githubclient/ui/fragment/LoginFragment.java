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
import com.example.githubclient.mvp.model.Tags;
import com.example.githubclient.mvp.model.entity.GithubUser;
import com.example.githubclient.mvp.presenter.LoginPresenter;
import com.example.githubclient.mvp.view.ILoginView;
import com.example.githubclient.ui.BackButtonListener;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class LoginFragment extends MvpAppCompatFragment implements ILoginView, BackButtonListener {

    private static final String TAG = LoginFragment.class.getSimpleName();
    private static final boolean INFO = true;

    private View mView;
    private GithubUser user;

    @InjectPresenter
    LoginPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_login, container, false);
        init(mView);
        return mView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            user = getArguments().getParcelable(Tags.USER_TAG);
        }
    }

    private void init(View view) {
        TextView loginTextView = view.findViewById(R.id.user_login);

        user.getLogin().subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                if (INFO) {
                    Log.i(TAG, "init.onSubscribe");
                }
            }

            @Override
            public void onNext(@NonNull String login) {
                if (INFO) {
                    Log.i(TAG, "init.onNext " + login);
                }
                loginTextView.setText(login);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                if (INFO) {
                    Log.i(TAG, "init.onError");
                }
            }

            @Override
            public void onComplete() {
                if (INFO) {
                    Log.i(TAG, "init.onComplete");
                }
            }
        });
    }

    @Override
    public boolean backPressed() {
        return mPresenter.backPressed();
    }
}