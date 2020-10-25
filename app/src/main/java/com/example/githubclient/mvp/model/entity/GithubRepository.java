package com.example.githubclient.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import io.reactivex.rxjava3.core.Observable;

public class GithubRepository implements Parcelable {

    @Expose
    String id;
    @Expose
    String name;
    @Expose
    String language;
    @Expose
    String userId;

    public GithubRepository(String id, String name, String language) {
        this.id = id;
        this.name = name;
        this.language = language;
    }

    protected GithubRepository(Parcel in) {
        id = in.readString();
        name = in.readString();
        language = in.readString();
        userId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(language);
        dest.writeString(userId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GithubRepository> CREATOR = new Creator<GithubRepository>() {
        @Override
        public GithubRepository createFromParcel(Parcel in) {
            return new GithubRepository(in);
        }

        @Override
        public GithubRepository[] newArray(int size) {
            return new GithubRepository[size];
        }
    };

    public String getId() {
        return id;
    }

    public Observable<String> getName() {
        return Observable.just(name);
    }

    public String getNameString() {
        return name;
    }

    public String getLanguage() {
        return language;
    }
}