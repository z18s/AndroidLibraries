package com.example.githubclient.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubclient.R;
import com.example.githubclient.mvp.presenter.UsersPresenter;
import com.example.githubclient.mvp.view.IUsersView;
import com.example.githubclient.ui.BackButtonListener;
import com.example.githubclient.ui.adapter.UserRVAdapter;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class UsersFragment extends MvpAppCompatFragment implements IUsersView, BackButtonListener {

    private RecyclerView mRecyclerView;
    private UserRVAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private View mView;

    @InjectPresenter
    UsersPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_users, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.rv_users);
        return mView;
    }

    @Override
    public void init() {
        mLayoutManager = new LinearLayoutManager(mView.getContext());

        mAdapter = new UserRVAdapter(mPresenter.getPresenter());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void updateList() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean backPressed() {
        return mPresenter.backPressed();
    }
}