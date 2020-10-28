package com.example.githubclient.mvp.presenter;

import androidx.annotation.UiThread;

import com.example.githubclient.GithubApplication;
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

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class UserPresenter extends MvpPresenter<IUserView> {

    private static final String TAG = UserPresenter.class.getSimpleName();

    @Inject
    IGithubRepositoriesRepo repositoriesRepo;
    @Inject
    Router router;
    @Inject
    Scheduler scheduler;

    private final GithubUser user;

    public UserPresenter(GithubUser user) {
        this.user = user;
        GithubApplication.INSTANCE.createRepositorySubcomponent().inject(this);
    }

    private class RepositoriesListPresenter implements IRepositoryListPresenter {
        private final List<GithubRepository> repositories = new ArrayList<>();

        @Override
        public void onItemClick(IRepositoryItemView view) {
            int index = view.getPos();

            Logger.showLog(Logger.INFO, TAG, "onItemClick " + index);

            GithubRepository repository = repositories.get(index);
            router.navigateTo(new Screens.RepositoryScreen(repository));
        }

        @Override
        public void bindView(IRepositoryItemView view) {
            GithubRepository repository = repositories.get(view.getPos());
            setRecyclerData(view, repository);
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
        setData();
    }

    @UiThread
    private void setData() {
        user.getLogin().subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@androidx.annotation.NonNull Disposable d) {
            }

            @Override
            public void onNext(@androidx.annotation.NonNull String login) {
                Logger.showLog(Logger.INFO, TAG, "setData.onNext - " + login);
                getViewState().setLogin(login);
            }

            @Override
            public void onError(@androidx.annotation.NonNull Throwable e) {
                Logger.showLog(Logger.INFO, TAG, "setData.onError");
            }

            @Override
            public void onComplete() {
                Logger.showLog(Logger.INFO, TAG, "setData.onComplete");
            }
        });

        repositoriesRepo.getRepositories(user).observeOn(scheduler).subscribe(
                (repositories) -> {
                    Logger.showLog(Logger.INFO, TAG, "setData.onNext " + repositories);
                    repositoryListPresenter.repositories.clear();
                    repositoryListPresenter.repositories.addAll(repositories);
                    getViewState().updateList();
                },
                (e) -> {
                    Logger.showLog(Logger.INFO, TAG, "setData.onError " + e.getMessage());
                }
        );
        getViewState().updateList();
    }

    private void setRecyclerData(IRepositoryItemView view, GithubRepository repository) {
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
                Logger.showLog(Logger.INFO, TAG, "setRecyclerData.onError");
            }

            @Override
            public void onComplete() {
                Logger.showLog(Logger.INFO, TAG, "setRecyclerData.onComplete");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getViewState().release();
    }

    public boolean backPressed() {
        router.exit();
        return true;
    }
}