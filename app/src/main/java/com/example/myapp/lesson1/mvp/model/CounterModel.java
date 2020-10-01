package com.example.myapp.lesson1.mvp.model;

import androidx.annotation.UiThread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UiThread
public class CounterModel {
    private List<Integer> mCounters = new ArrayList<>(Arrays.asList(0,0,0));

    public int getCurrent(int index) {
        return mCounters.get(index);
    }

    public int next(int index) {
        Integer nextValue = mCounters.get(index) + 1;

        mCounters.set(index, nextValue);

        return nextValue;
    }

    public void set(int index, int value) {
        mCounters.set(index, value);
    }
}