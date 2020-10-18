package com.example.githubclient.mvp.model;

import com.example.githubclient.ApiHolder;
import com.example.githubclient.mvp.model.api.IDataSource;

public class Model {
    private static volatile Model INSTANCE;
    private ApiHolder apiHolder;

    public static Model getInstance() {
        Model localRef = INSTANCE;

        if (localRef == null) {
            synchronized (Model.class) {
                INSTANCE = localRef;
                if (localRef == null) {
                    INSTANCE = localRef = new Model();
                }
            }
        }
        return localRef;
    }

    public synchronized IDataSource getApi() {
        return apiHolder.getDataSource();
    }
}