package com.example.githubclient.ui.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;

import com.example.githubclient.GithubApplication;
import com.example.githubclient.Logger;
import com.example.githubclient.mvp.model.network.INetworkStatus;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class AndroidNetworkStatus implements INetworkStatus {

    private static final String TAG = AndroidNetworkStatus.class.getSimpleName();

    private BehaviorSubject<Boolean> statusObject = BehaviorSubject.create();

    public AndroidNetworkStatus() {
        statusObject.onNext(false);

        ConnectivityManager connectivityManager = (ConnectivityManager) GithubApplication.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkRequest networkRequest = new NetworkRequest.Builder().build();

        connectivityManager.registerNetworkCallback(networkRequest, new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                Logger.showLog(Logger.INFO, TAG, "onAvailable");
                statusObject.onNext(true);
            }

            @Override
            public void onUnavailable() {
                super.onUnavailable();
                Logger.showLog(Logger.INFO, TAG, "onUnavailable");
                statusObject.onNext(false);
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                Logger.showLog(Logger.INFO, TAG, "onLost");
                statusObject.onNext(false);
            }
        });
    }

    @Override
    public Observable<Boolean> isOnline() {
        return statusObject;
    }

    @Override
    public Single<Boolean> isOnlineSingle() {
        return statusObject.first(false);
    }
}