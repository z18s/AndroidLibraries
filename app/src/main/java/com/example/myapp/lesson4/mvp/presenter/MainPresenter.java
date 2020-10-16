package com.example.myapp.lesson4.mvp.presenter;

import android.util.Log;

import com.example.myapp.lesson4.Logger;
import com.example.myapp.lesson4.mvp.model.ConverterModel;
import com.example.myapp.lesson4.mvp.view.MainView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import moxy.MvpPresenter;

public class MainPresenter extends MvpPresenter<MainView> {

    private final String TAG = MainPresenter.class.getSimpleName();

    private ConverterModel model = new ConverterModel();

    private Disposable disposable;

    public String getJpgPath() {
        return model.getJpgPath();
    }

    public String getPngPath() {
        return model.getPngPath();
    }

    public void convertImage() {
        disposable = model.getFlowable()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((bitmap) -> {
                    getViewState().updateConvertedImage();
                });
    }

    public void cancelConversion() {
        if (Logger.VERBOSE) {
            Log.v(TAG, "cancelConversion");
        }
        disposable.dispose();
    }

    public boolean isConversationComplete() {
        if (Logger.VERBOSE) {
            Log.v(TAG, "isConversationComplete");
        }
        return model.isComplete();
    }
}