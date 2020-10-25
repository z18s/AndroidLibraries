package com.example.githubclient.mvp.model.entity.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RoomGithubUser {

    @PrimaryKey @NonNull
    public String id;
    @ColumnInfo(name = "login")
    public String login;
    @ColumnInfo(name = "avatar_url")
    public String avatarUrl;
    @ColumnInfo(name = "repositories_url")
    public String reposUrl;

    public RoomGithubUser(@NonNull String id, String login, String avatarUrl, String reposUrl) {
        this.id = id;
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.reposUrl = reposUrl;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getReposUrl() {
        return reposUrl;
    }
}