package com.example.githubclient.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.githubclient.GithubApplication;
import com.example.githubclient.Logger;
import com.example.githubclient.R;
import com.example.githubclient.mvp.presenter.MainPresenter;
import com.example.githubclient.mvp.view.IMainView;

import javax.inject.Inject;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;

public class MainActivity extends MvpAppCompatActivity implements IMainView {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    NavigatorHolder navigatorHolder;
    private final Navigator navigator = new SupportAppNavigator(this, getSupportFragmentManager(), R.id.container);

    @InjectPresenter
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GithubApplication.INSTANCE.getAppComponent().inject(this);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        super.onPause();
        navigatorHolder.removeNavigator();
    }

    @Override
    public void onBackPressed() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            Logger.showLog(Logger.INFO, TAG, "onBackPressed.fragment - " + fragment);
            if (fragment instanceof BackButtonListener && ((BackButtonListener) fragment).backPressed()) {
                Logger.showLog(Logger.INFO, TAG, "onBackPressed - return");
                return;
            }
        }
        presenter.backClicked();
    }
}