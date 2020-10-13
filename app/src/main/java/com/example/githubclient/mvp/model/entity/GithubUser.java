package com.example.githubclient.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import io.reactivex.rxjava3.core.Observable;

public class GithubUser implements Parcelable {
    private String mLogin;

    public GithubUser(String login) {
        mLogin = login;
    }

    protected GithubUser(Parcel in) {
        mLogin = in.readString();
    }

    public Observable<String> getLogin() {
        return Observable.just(mLogin);
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
        parcel.writeString(mLogin);
    }
}