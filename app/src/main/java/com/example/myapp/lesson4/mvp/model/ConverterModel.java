package com.example.myapp.lesson4.mvp.model;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.myapp.lesson4.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import io.reactivex.rxjava3.core.Flowable;

public class ConverterModel {

    private final String TAG = ConverterModel.class.getSimpleName();

    @SuppressLint("SdCardPath")
    private Pic jpgPic = new Pic("/sdcard/Pictures/test.jpg");
    @SuppressLint("SdCardPath")
    private Pic pngPic = new Pic("/sdcard/Pictures/test.png");

    private boolean isComplete = false;

    private void convertImage() {

        convertToPng(pngPic);

        try {
            pngPic.setBitmap(pngPic.createBitmap());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void convertToPng(Pic png) {

        if (png.getFile().exists()) {
            png.deleteFile();
            if (Logger.VERBOSE) {
                Log.v(TAG, "convertToPng.delete");
            }
        }

        try {
            FileOutputStream out = new FileOutputStream(png.getFile());
            isComplete = pngPic.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, out);
            if (Logger.VERBOSE) {
                Log.v(TAG, "convertToPng.isComplete - " + isComplete);
            }
            if (isComplete) {
                if (Logger.VERBOSE) {
                    Log.v(TAG, "convertToPng.out - " + out);
                }
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Flowable<Bitmap> getFlowable() {
        return Flowable.just(pngPic.getBitmap()).doOnNext((bitmap) -> {
            if (Logger.VERBOSE) {
                Log.v(TAG, "Thread.sleep");
            }
            Thread.sleep(5000);
            ConverterModel.this.convertImage();
        });
    }

    public String getJpgPath() {
        return jpgPic.getPath();
    }

    public String getPngPath() {
        return pngPic.getPath();
    }

    public boolean isComplete() {
        return isComplete;
    }
}