package com.example.githubclient.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.githubclient.R;
import com.example.githubclient.mvp.model.Tags;
import com.example.githubclient.mvp.model.entity.GithubRepository;
import com.example.githubclient.mvp.presenter.RepositoryPresenter;
import com.example.githubclient.mvp.view.IRepositoryView;
import com.example.githubclient.ui.BackButtonListener;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class RepositoryFragment extends MvpAppCompatFragment implements IRepositoryView, BackButtonListener {

    private static final String TAG = RepositoryFragment.class.getSimpleName();

    private View view;
    TextView repositoryNameTextView;
    TextView repositoryLanguageTextView;

    @InjectPresenter
    RepositoryPresenter presenter;

    @ProvidePresenter
    RepositoryPresenter provideRepositoryPresenter() {
        return new RepositoryPresenter(getGithubRepository());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_repository, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private GithubRepository getGithubRepository() {
        return ((getArguments() != null) ? getArguments().getParcelable(Tags.REPOSITORY_TAG) : null);
    }

    @Override
    public void init() {
        repositoryNameTextView = view.findViewById(R.id.repository_name);
        repositoryLanguageTextView = view.findViewById(R.id.repository_language);
    }

    @Override
    public void setName(String name) {
        repositoryNameTextView.setText(name);
    }

    @Override
    public void setLanguage(String language) {
        repositoryLanguageTextView.setText(String.format("%s %s", getResources().getString(R.string.language_title), language));
    }

    @Override
    public boolean backPressed() {
        return presenter.backPressed();
    }
}