package com.example.myapp.lesson1.mvp.presenter;

import com.example.myapp.lesson1.R;
import com.example.myapp.lesson1.mvp.model.CounterModel;
import com.example.myapp.lesson1.mvp.view.MainView;

public class MainPresenter {
    private CounterModel mModel = new CounterModel();
    private MainView mView;

    public MainPresenter(MainView view) {
        if (view == null) throw new IllegalArgumentException("View cannot be null");

        mView = view;
    }

    public void counterClick(int id) {
        switch (id) {
            case R.id.btn_counter1:
                mView.setButtonText(0, String.valueOf(mModel.next(0)));
                break;

            case R.id.btn_counter2:
                mView.setButtonText(1, String.valueOf(mModel.next(1)));
                break;

            case R.id.btn_counter3:
                mView.setButtonText(2, String.valueOf(mModel.next(2)));
                break;
        }
    }
}