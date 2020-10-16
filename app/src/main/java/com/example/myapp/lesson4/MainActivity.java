package com.example.myapp.lesson4;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapp.lesson4.mvp.presenter.MainPresenter;
import com.example.myapp.lesson4.mvp.view.MainView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    private final String TAG = MainActivity.class.getSimpleName();
    private final String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private final int REQ_CODE = 11;

    private ImageView imageJpgView;
    private ImageView imagePngView;
    private Button chooseButton;
    private Button convertButton;
    private Button cancelButton;

    @InjectPresenter
    MainPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lesson4);

        if (hasStoragePermissions()) {
            if (Logger.VERBOSE) {
                Log.v(TAG, "onCreate - init");
            }
            init();
        } else {
            if (Logger.VERBOSE) {
                Log.v(TAG, "onCreate - requestPermissions");
            }
            requestPermissions();
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, PERMISSIONS, REQ_CODE);
    }

    private boolean hasStoragePermissions() {
        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == REQ_CODE) {
                init();
            }
        } else {
            Toast.makeText(this, "Please give your permissions.", Toast.LENGTH_LONG).show();
        }
    }

    private void init() {
        initViews();
        initListeners();
    }

    private void initViews() {
        imageJpgView = findViewById(R.id.l4_image_jpg);
        imagePngView = findViewById(R.id.l4_image_png);
        chooseButton = findViewById(R.id.l4_btn_choose);
        convertButton = findViewById(R.id.l4_btn_convert);
        cancelButton = findViewById(R.id.l4_btn_convert_cancel);

        cancelButton.setVisibility(View.GONE);
    }

    private void initListeners() {
        chooseButton.setOnClickListener(view -> {
            if (Logger.VERBOSE) {
                Log.v(TAG, "chooseButton.OnClickListener");
            }

            setImage(imageJpgView, presenter.getJpgPath());
        });

        convertButton.setOnClickListener(view -> {
            if (Logger.VERBOSE) {
                Log.v(TAG, "convertButton.OnClickListener");
            }

            setCancelButton();

            convertImage();
        });

        cancelButton.setOnClickListener(view -> {
            if (Logger.VERBOSE) {
                Log.v(TAG, "cancelButton.OnClickListener");
            }

            setConversionButton();

            cancelConversion();
        });
    }

    private void setConversionButton() {
        cancelButton.setVisibility(View.GONE);
        convertButton.setVisibility(View.VISIBLE);
    }

    private void setCancelButton() {
        convertButton.setVisibility(View.GONE);
        cancelButton.setVisibility(View.VISIBLE);
    }

    public void setImage(ImageView imageView, String path) {
        Bitmap bitmap = null;
        try {
            bitmap = createBitmap(path);
            if (Logger.VERBOSE) {
                Log.v(TAG, "setImage - " + path);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }

        if (Logger.VERBOSE) {
            Log.v(TAG, "setImage - " + bitmap);
        }
    }

    @Override
    public void convertImage() {
        presenter.convertImage();
    }

    @Override
    public void cancelConversion() {
        presenter.cancelConversion();
    }

    @Override
    public void updateConvertedImage() {
        setImage(imagePngView, presenter.getPngPath());
        Toast.makeText(this, "Conversion's " + ((presenter.isConversationComplete()) ? "passed" : "canceled"), Toast.LENGTH_LONG).show();
        setConversionButton();
    }

    private Bitmap createBitmap(String path) throws FileNotFoundException {
        if (Logger.VERBOSE) {
            Log.v(TAG, "createBitmap - " + path);
        }
        return BitmapFactory.decodeStream(new FileInputStream(new File(path)));
    }
}