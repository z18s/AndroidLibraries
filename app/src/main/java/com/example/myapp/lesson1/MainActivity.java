package com.example.myapp.lesson1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.lesson1.mvp.presenter.MainPresenter;
import com.example.myapp.lesson1.mvp.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener {

    private Button mBtnCounter1;
    private Button mBtnCounter2;
    private Button mBtnCounter3;

    private MainPresenter mPresenter = new MainPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnCounter1 = findViewById(R.id.btn_counter1);
        mBtnCounter2 = findViewById(R.id.btn_counter2);
        mBtnCounter3 = findViewById(R.id.btn_counter3);

        mBtnCounter1.setOnClickListener(this);
        mBtnCounter2.setOnClickListener(this);
        mBtnCounter3.setOnClickListener(this);
    }

    @Override
    public void setButtonText(int index, @NonNull String text) {
        switch (index) {
            case 0:
                mBtnCounter1.setText(text);
                break;
            case 1:
                mBtnCounter2.setText(text);
                break;
            case 2:
                mBtnCounter3.setText(text);
                break;
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_counter1:
                mPresenter.counterClick(0);
                break;
            case R.id.btn_counter2:
                mPresenter.counterClick(1);
                break;
            case R.id.btn_counter3:
                mPresenter.counterClick(2);
                break;
        }
    }
}