package com.example.githubclient.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubclient.GithubApplication;
import com.example.githubclient.R;
import com.example.githubclient.mvp.model.repo.IGithubUsersRepo;
import com.example.githubclient.mvp.model.repo.retrofit.RetrofitGithubUsersRepo;
import com.example.githubclient.mvp.presenter.UsersPresenter;
import com.example.githubclient.mvp.view.IUsersView;
import com.example.githubclient.ui.BackButtonListener;
import com.example.githubclient.ui.adapter.UserRVAdapter;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.terrakok.cicerone.Router;

public class UsersFragment extends MvpAppCompatFragment implements IUsersView, BackButtonListener {

    private RecyclerView recyclerView;
    private UserRVAdapter adapter;

    private View view;

    @InjectPresenter
    UsersPresenter presenter;

    @ProvidePresenter
    UsersPresenter provideUsersPresenter() {
        IGithubUsersRepo usersRepo = new RetrofitGithubUsersRepo((GithubApplication.INSTANCE).getApi());
        Router router = GithubApplication.INSTANCE.getRouter();
        return new UsersPresenter(AndroidSchedulers.mainThread(), usersRepo, router);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_users, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_users);
        return view;
    }

    @Override
    public void init() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        adapter = new UserRVAdapter(presenter.getPresenter());
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