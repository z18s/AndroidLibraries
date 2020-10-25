package com.example.githubclient.mvp.model.entity.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(
        entity = RoomGithubUser.class,
        parentColumns = {"id"},
        childColumns = {"user_id"},
        onDelete = ForeignKey.CASCADE)})
public class RoomGithubRepository {

    @PrimaryKey @NonNull
    public String id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "language")
    public String language;
    @ColumnInfo(name = "user_id")
    public String userId;

    public RoomGithubRepository(@NonNull String id, String name, String language, String userId) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.userId = userId;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }
}