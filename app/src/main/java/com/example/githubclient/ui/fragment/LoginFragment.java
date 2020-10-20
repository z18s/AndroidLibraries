package com.example.githubclient.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubclient.GithubApplication;
import com.example.githubclient.Logger;
import com.example.githubclient.R;
import com.example.githubclient.mvp.model.Tags;
import com.example.githubclient.mvp.model.entity.GithubUser;
import com.example.githubclient.mvp.model.repo.IGithubRepositoriesRepo;
import com.example.githubclient.mvp.model.repo.retrofit.RetrofitGithubRepositoriesRepo;
import com.example.githubclient.mvp.presenter.LoginPresenter;
import com.example.githubclient.mvp.view.ILoginView;
import com.example.githubclient.ui.BackButtonListener;
import com.example.githubclient.ui.adapter.RepositoryRVAdapter;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.terrakok.cicerone.Router;

public class LoginFragment extends MvpAppCompatFragment implements ILoginView, BackButtonListener {

    private static final String TAG = LoginFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private RepositoryRVAdapter adapter;

    private View view;
    private GithubUser user;

    @InjectPresenter
    LoginPresenter presenter;

    @ProvidePresenter
    LoginPresenter provideLoginPresenter() {
        IGithubRepositoriesRepo repositoriesRepo = new RetrofitGithubRepositoriesRepo((GithubApplication.INSTANCE).getApi());
        Router router = GithubApplication.INSTANCE.getRouter();
        user = getGithubUser();
        return new LoginPresenter(AndroidSchedulers.mainThread(), repositoriesRepo, router, user);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_repos);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getGithubUser();
    }

    private GithubUser getGithubUser() {
        if (getArguments() != null) {
            return getArguments().getParcelable(Tags.USER_TAG);
        }
        return null;
    }

    @Override
    public void init() {
        TextView loginTextView = view.findViewById(R.id.user_login);

        user.getLogin().subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Logger.showLog(Logger.INFO, TAG, "init.onSubscribe");
            }

            @Override
            public void onNext(@NonNull String login) {
                Logger.showLog(Logger.INFO, TAG, "init.onNext " + login);
                loginTextView.setText(login);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Logger.showLog(Logger.INFO, TAG, "init.onError");
            }

            @Override
            public void onComplete() {
                Logger.showLog(Logger.INFO, TAG, "init.onComplete");
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        adapter = new RepositoryRVAdapter(presenter.getPresenter());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void updateList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean backPressed() {
        return presenter.backPressed();
    }
}