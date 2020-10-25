package com.example.githubclient.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.githubclient.Logger;
import com.example.githubclient.R;
import com.example.githubclient.mvp.model.Tags;
import com.example.githubclient.mvp.model.entity.GithubRepository;
import com.example.githubclient.mvp.presenter.RepositoryPresenter;
import com.example.githubclient.mvp.view.IRepositoryView;
import com.example.githubclient.ui.BackButtonListener;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class RepositoryFragment extends MvpAppCompatFragment implements IRepositoryView, BackButtonListener {

    private static final String TAG = RepositoryFragment.class.getSimpleName();

    private View view;

    @InjectPresenter
    RepositoryPresenter presenter;

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
        TextView repositoryNameTextView = view.findViewById(R.id.repository_name);
        TextView repositoryLanguageTextView = view.findViewById(R.id.repository_language);
        String title = getResources().getString(R.string.language_title);
        GithubRepository repository = getGithubRepository();

        repository.getName().subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Logger.showLog(Logger.INFO, TAG, "init.onSubscribe");
            }

            @Override
            public void onNext(@NonNull String name) {
                Logger.showLog(Logger.INFO, TAG, "init.onNext " + name);
                repositoryNameTextView.setText(name);
                repositoryLanguageTextView.setText(title + repository.getLanguage());
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
    }

    @Override
    public boolean backPressed() {
        return presenter.backPressed();
    }
}