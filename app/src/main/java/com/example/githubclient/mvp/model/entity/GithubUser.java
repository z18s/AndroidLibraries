package com.example.githubclient.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import io.reactivex.rxjava3.core.Observable;

public class GithubUser implements Parcelable {
    private String login;

    public GithubUser(String login) {
        this.login = login;
    }

    protected GithubUser(Parcel in) {
        login = in.readString();
    }

    public Observable<String> getLogin() {
        return Observable.just(login);
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
        parcel.writeString(login);
    }
}