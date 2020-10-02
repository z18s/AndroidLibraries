package com.example.githubclient.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.githubclient.GithubApplication;
import com.example.githubclient.R;
import com.example.githubclient.mvp.presenter.MainPresenter;
import com.example.githubclient.mvp.view.IMainView;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;

public class MainActivity extends MvpAppCompatActivity implements IMainView {

    private NavigatorHolder mNavigatorHolder = GithubApplication.INSTANCE.getNavigatorHolder();
    Navigator mNavigator = new SupportAppNavigator(this, getSupportFragmentManager(), R.id.container);

    @InjectPresenter
    MainPresenter mPresenter;

    @ProvidePresenter
    MainPresenter provideMainPresenter() {
        Router router = ((GithubApplication) GithubApplication.getAppContext()).getRouter();

        return new MainPresenter(router);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        mNavigatorHolder.setNavigator(mNavigator);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mNavigatorHolder.removeNavigator();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof BackButtonListener && ((BackButtonListener) fragment).backPressed()) {
                return;
            }
        }

        mPresenter.backClicked();
    }
}