package com.example.githubclient.mvp.model.entity.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.githubclient.mvp.model.entity.room.RoomGithubRepository;

import java.util.List;

@Dao
public interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RoomGithubRepository repository);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<RoomGithubRepository> repositories);

    @Update
    void update(RoomGithubRepository repository);

    @Update
    void update(List<RoomGithubRepository> repositories);

    @Delete
    void delete(RoomGithubRepository repository);

    @Delete
    void delete(List<RoomGithubRepository> repositories);


    @Query("SELECT * FROM RoomGithubRepository")
    List<RoomGithubRepository> getAll();

    @Query("SELECT * FROM RoomGithubRepository WHERE user_id = :userId")
    List<RoomGithubRepository> findByUser(String userId);
}