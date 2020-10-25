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
import com.example.githubclient.mvp.model.entity.room.Database;
import com.example.githubclient.mvp.model.repo.IGithubRepositoriesRepo;
import com.example.githubclient.mvp.model.repo.retrofit.RetrofitGithubRepositoriesRepo;
import com.example.githubclient.mvp.presenter.UserPresenter;
import com.example.githubclient.mvp.view.IUserView;
import com.example.githubclient.ui.BackButtonListener;
import com.example.githubclient.ui.adapter.RepositoryRVAdapter;
import com.example.githubclient.ui.network.AndroidNetworkStatus;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.terrakok.cicerone.Router;

public class UserFragment extends MvpAppCompatFragment implements IUserView, BackButtonListener {

    private static final String TAG = UserFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private RepositoryRVAdapter adapter;

    private View view;

    @InjectPresenter
    UserPresenter presenter;

    @ProvidePresenter
    UserPresenter provideLoginPresenter() {
        IGithubRepositoriesRepo repositoriesRepo = new RetrofitGithubRepositoriesRepo((GithubApplication.INSTANCE).getApi(),
                new AndroidNetworkStatus(),
                Database.getInstance());
        Router router = GithubApplication.INSTANCE.getRouter();
        return new UserPresenter(AndroidSchedulers.mainThread(), repositoriesRepo, router, getGithubUser());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
        recyclerView = view.findViewById(R.id.rv_repositories);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private GithubUser getGithubUser() {
        return ((getArguments() != null) ? getArguments().getParcelable(Tags.USER_TAG) : null);
    }

    @Override
    public void init() {
        TextView userLoginTextView = view.findViewById(R.id.user_login);
        GithubUser githubUser = getGithubUser();

        githubUser.getLogin().subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Logger.showLog(Logger.INFO, TAG, "init.onSubscribe");
            }

            @Override
            public void onNext(@NonNull String login) {
                Logger.showLog(Logger.INFO, TAG, "init.onNext " + login);
                userLoginTextView.setText(login);
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