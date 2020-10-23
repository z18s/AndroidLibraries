package com.example.githubclient.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import io.reactivex.rxjava3.core.Observable;

public class GithubUser implements Parcelable {

    @Expose
    int id;
    @Expose
    String login;
    @Expose
    String avatarUrl;
    @Expose
    String reposUrl;

    public GithubUser(int id, String login, String avatarUrl, String reposUrl) {
        this.id = id;
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.reposUrl = reposUrl;
    }

    protected GithubUser(Parcel in) {
        id = in.readInt();
        login = in.readString();
        avatarUrl = in.readString();
        reposUrl = in.readString();
    }

    public static final Creator<GithubUser> CREATOR = new Creator<GithubUser>() {
        @Override
        public GithubUser createFromParcel(Parcel in) {
            return new GithubUser(in);
        }

        @Override
        public GithubUser[] newArray(int size) {
            return new GithubUser[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(login);
        parcel.writeString(avatarUrl);
        parcel.writeString(reposUrl);
    }

    public int getId() {
        return id;
    }

    public Observable<String> getLogin() {
        return Observable.just(login);
    }

    public String getLoginString() {
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getRepositoriesUrl() {
        return reposUrl;
    }
}