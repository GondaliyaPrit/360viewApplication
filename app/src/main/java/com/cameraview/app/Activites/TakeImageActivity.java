package com.cameraview.app.Activites;

import android.content.Context;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.cameraview.app.databinding.ActivityTakeImageBinding;
import com.cameraview.app.helper.CameraPreview;
import com.cameraview.app.helper.CameraPreviewViewModel;

import java.util.List;

public class TakeImageActivity extends AppCompatActivity {
    private Camera mCamera;
    private CameraPreview mCameraPreview;
    ActivityTakeImageBinding binding;
    CameraPreviewViewModel cameraPreviewViewModel;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTakeImageBinding.inflate(getLayoutInflater());
        initvariable();
        setdata();
        initclicklistner();
        setContentView(binding.getRoot());
    }

    private void initvariable() {
        cameraPreviewViewModel = new CameraPreviewViewModel(this,binding);
        context = this;
    }

    private void setdata() {
        cameraPreviewViewModel.startCameraPreview();
        cameraPreviewViewModel.captureImage();
        binding.imgseek.setMax(360);
    }

    private void initclicklistner() {
        binding.imgseek.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


    }

}