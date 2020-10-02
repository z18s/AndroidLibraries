package com.example.myapp.lesson1.mvp.presenter;

import com.example.myapp.lesson1.mvp.model.CounterModel;
import com.example.myapp.lesson1.mvp.view.MainView;

public class MainPresenter {
    private CounterModel mModel = new CounterModel();
    private MainView mView;

    public MainPresenter(MainView view) {
        if (view == null) throw new IllegalArgumentException("View cannot be null");

        mView = view;
    }

    public void counterClick(int index) {
        mView.setButtonText(index, String.valueOf(mModel.next(index)));
    }
}