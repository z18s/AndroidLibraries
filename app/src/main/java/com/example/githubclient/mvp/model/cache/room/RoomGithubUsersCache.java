package com.example.githubclient.mvp.model.cache.room;

import com.example.githubclient.Logger;
import com.example.githubclient.mvp.model.cache.IGithubUsersCache;
import com.example.githubclient.mvp.model.entity.GithubUser;
import com.example.githubclient.mvp.model.entity.room.Database;
import com.example.githubclient.mvp.model.entity.room.RoomGithubUser;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class RoomGithubUsersCache implements IGithubUsersCache {

    private static final String TAG = RoomGithubUsersCache.class.getSimpleName();

    final Database db;

    public RoomGithubUsersCache(Database db) {
        this.db = db;
    }

    @Override
    public void putUsers(List<GithubUser> users) {
        List<RoomGithubUser> roomGithubUsers = new ArrayList<>();
        for (GithubUser user : users) {
            RoomGithubUser roomGithubUser = new RoomGithubUser(
                    user.getId(),
                    user.getLoginString(),
                    user.getAvatarUrl(),
                    user.getRepositoriesUrl()
            );
            roomGithubUsers.add(roomGithubUser);
        }
        Logger.showLog(Logger.INFO, TAG, "putUsers - " + roomGithubUsers.size());
        db.userDao().insert(roomGithubUsers);
    }

    @Override
    public Single<List<GithubUser>> getUsers() {
        List<RoomGithubUser> roomGithubUsers = db.userDao().getAll();
        List<GithubUser> users = new ArrayList<>();
        for (RoomGithubUser roomGithubUser : roomGithubUsers) {
            GithubUser githubUser = new GithubUser(
                    roomGithubUser.getId(),
                    roomGithubUser.getLogin(),
                    roomGithubUser.getAvatarUrl(),
                    roomGithubUser.getReposUrl()
            );
            users.add(githubUser);
        }
        Logger.showLog(Logger.INFO, TAG, "getUsers - " + users.size());
        return Single.just(users);
    }
}