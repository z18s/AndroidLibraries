package com.example.myapp.lesson4.mvp.view;

import moxy.MvpView;
import moxy.viewstate.strategy.alias.AddToEndSingle;

@AddToEndSingle
public interface MainView extends MvpView {
    void convertImage();
    void updateConvertedImage();
    void cancelConversion();
}