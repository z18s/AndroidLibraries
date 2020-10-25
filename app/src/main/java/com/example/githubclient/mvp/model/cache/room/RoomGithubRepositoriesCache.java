package com.example.githubclient.mvp.model.cache.room;

import com.example.githubclient.Logger;
import com.example.githubclient.mvp.model.cache.IGithubRepositoriesCache;
import com.example.githubclient.mvp.model.entity.GithubRepository;
import com.example.githubclient.mvp.model.entity.GithubUser;
import com.example.githubclient.mvp.model.entity.room.Database;
import com.example.githubclient.mvp.model.entity.room.RoomGithubRepository;
import com.example.githubclient.mvp.model.entity.room.RoomGithubUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class RoomGithubRepositoriesCache implements IGithubRepositoriesCache {

    private static final String TAG = RoomGithubRepositoriesCache.class.getSimpleName();

    final Database db;

    public RoomGithubRepositoriesCache(Database db) {
        this.db = db;
    }

    @Override
    public void putRepositories(GithubUser user, List<GithubRepository> repositories) {
        RoomGithubUser roomGithubUser = db.userDao().findByLogin(user.getLoginString());
        List<RoomGithubRepository> roomGithubRepositories = new ArrayList<>();
        for (GithubRepository repository : repositories) {
            RoomGithubRepository roomGithubRepository = new RoomGithubRepository(
                    repository.getId(),
                    repository.getNameString(),
                    repository.getLanguage(),
                    roomGithubUser.getId()
            );
            roomGithubRepositories.add(roomGithubRepository);
        }
        Logger.showLog(Logger.INFO, TAG, String.format("putRepositories - %s: %s", roomGithubUser.getLogin(), roomGithubRepositories.size()));
        db.repositoryDao().insert(roomGithubRepositories);
    }

    @Override
    public Single<List<GithubRepository>> getRepositories(GithubUser user) {
        RoomGithubUser roomGithubUser = db.userDao().findByLogin(user.getLoginString());
        List<RoomGithubRepository> roomGithubRepositories = db.repositoryDao().findByUser(roomGithubUser.getId());
        List<GithubRepository> repositories = new ArrayList<>();
        for (RoomGithubRepository roomGithubRepository : roomGithubRepositories) {
            GithubRepository githubRepository = new GithubRepository(
                    roomGithubRepository.getId(),
                    roomGithubRepository.getName(),
                    roomGithubRepository.getLanguage()
            );
            repositories.add(githubRepository);
        }
        Logger.showLog(Logger.INFO, TAG, String.format("getRepositories - %s: %s", roomGithubUser.getLogin(), roomGithubRepositories.size()));
        return Single.just(repositories);
    }
}