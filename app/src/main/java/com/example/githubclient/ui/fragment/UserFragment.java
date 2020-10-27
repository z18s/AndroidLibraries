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

import com.example.githubclient.R;
import com.example.githubclient.mvp.model.Tags;
import com.example.githubclient.mvp.model.entity.GithubUser;
import com.example.githubclient.mvp.presenter.UserPresenter;
import com.example.githubclient.mvp.view.IUserView;
import com.example.githubclient.ui.BackButtonListener;
import com.example.githubclient.ui.adapter.RepositoryRVAdapter;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class UserFragment extends MvpAppCompatFragment implements IUserView, BackButtonListener {

    private static final String TAG = UserFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private RepositoryRVAdapter adapter;
    private View view;
    TextView userLoginTextView;

    @InjectPresenter
    UserPresenter presenter;

    @ProvidePresenter
    UserPresenter provideLoginPresenter() {
        return new UserPresenter(getGithubUser());
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
        userLoginTextView = view.findViewById(R.id.user_login);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        adapter = new RepositoryRVAdapter(presenter.getPresenter());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setLogin(String login) {
        userLoginTextView.setText(login);
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