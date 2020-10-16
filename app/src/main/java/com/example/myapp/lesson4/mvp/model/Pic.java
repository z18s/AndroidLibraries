package com.example.myapp.lesson4.mvp.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.myapp.lesson4.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Pic {

    private final String TAG = Pic.class.getSimpleName();

    private String path;
    private File file;
    private Bitmap bitmap;

    public Pic(String path) {
        this.path = path;
        this.file = new File(path);

        try {
            bitmap = createBitmap();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Bitmap createBitmap() throws ExecutionException, InterruptedException {
        FutureTask<Bitmap> future = new FutureTask<>((Callable<Bitmap>) () -> {
            if (getFile().exists()) {
                try (FileInputStream inputStream = new FileInputStream(getFile())) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    if (Logger.VERBOSE) {
                        Log.v(TAG, "createBitmap.bitmap - " + bitmap);
                    }
                    return bitmap;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        });
        new Thread(future).start();

        return future.get();
    }

    public String getPath() {
        return path;
    }

    public File getFile() {
        return file;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void deleteFile() {
        file.delete();
    }
}