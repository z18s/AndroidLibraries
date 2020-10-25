package com.example.githubclient.mvp.presenter;

import androidx.annotation.UiThread;

import com.example.githubclient.Logger;
import com.example.githubclient.mvp.model.entity.GithubRepository;
import com.example.githubclient.mvp.model.entity.GithubUser;
import com.example.githubclient.mvp.model.repo.IGithubRepositoriesRepo;
import com.example.githubclient.mvp.presenter.list.IRepositoryListPresenter;
import com.example.githubclient.mvp.view.IRepositoryItemView;
import com.example.githubclient.mvp.view.IUserView;
import com.example.githubclient.navigation.Screens;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class UserPresenter extends MvpPresenter<IUserView> {

    private static final String TAG = UserPresenter.class.getSimpleName();

    private final IGithubRepositoriesRepo REPOSITORIES_REPO;
    private final Router ROUTER;
    private final Scheduler SCHEDULER;

    private final GithubUser USER;

    public UserPresenter(Scheduler scheduler, IGithubRepositoriesRepo repositoriesRepo, Router router, GithubUser user) {
        SCHEDULER = scheduler;
        REPOSITORIES_REPO = repositoriesRepo;
        ROUTER = router;
        USER = user;
    }

    private class RepositoriesListPresenter implements IRepositoryListPresenter {
        private final List<GithubRepository> repositories = new ArrayList<>();

        @Override
        public void onItemClick(IRepositoryItemView view) {
            int index = view.getPos();

            Logger.showLog(Logger.INFO, TAG, " onItemClick " + index);

            GithubRepository repository = repositories.get(index);
            ROUTER.navigateTo(new Screens.RepositoryScreen(repository));
        }

        @Override
        public void bindView(IRepositoryItemView view) {
            GithubRepository repository = repositories.get(view.getPos());
            setData(view, repository);
        }

        @Override
        public int getCount() {
            return repositories.size();
        }
    }

    private final UserPresenter.RepositoriesListPresenter repositoryListPresenter = new UserPresenter.RepositoriesListPresenter();

    public IRepositoryListPresenter getPresenter() {
        return repositoryListPresenter;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().init();
        loadData();
    }

    @UiThread
    private void loadData() {
        REPOSITORIES_REPO.getRepositories(USER).observeOn(SCHEDULER).subscribe(
                (repositories) -> {
                    Logger.showLog(Logger.INFO, TAG, "loadData.onNext " + repositories);
                    repositoryListPresenter.repositories.clear();
                    repositoryListPresenter.repositories.addAll(repositories);
                    getViewState().updateList();
                },
                (e) -> {
                    Logger.showLog(Logger.INFO, TAG, "loadData.onError " + e.getMessage());
                }
        );

        getViewState().updateList();
    }

    private void setData(IRepositoryItemView view, GithubRepository repository) {
        repository.getName().subscribe(new Observer<String>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull String name) {
                view.setRepositoryName(name);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Logger.showLog(Logger.INFO, TAG, "setData.onError");
            }

            @Override
            public void onComplete() {
                Logger.showLog(Logger.INFO, TAG, "setData.onComplete");
            }
        });
    }

    public boolean backPressed() {
        ROUTER.exit();
        return true;
    }
}