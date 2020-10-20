package com.example.githubclient.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import io.reactivex.rxjava3.core.Observable;

public class GithubUser implements Parcelable {
    @Expose
    String login;
    @Expose
    String avatarUrl;
    @Expose
    String reposUrl;

    protected GithubUser(Parcel in) {
        login = in.readString();
        avatarUrl = in.readString();
        reposUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(login);
        dest.writeString(avatarUrl);
        dest.writeString(reposUrl);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public Observable<String> getLogin() {
        return Observable.just(login);
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getRepositoriesUrl() {
        return reposUrl;
    }
}