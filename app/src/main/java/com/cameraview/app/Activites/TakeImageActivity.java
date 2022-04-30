package com.cameraview.app.Activites;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Gravity;
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
    }

    private void setdata() {
        cameraPreviewViewModel.startCameraPreview();
        cameraPreviewViewModel.captureImage();
    }

    private void initclicklistner() {
    }

    private Camera getCameraInstance() {
        Camera camera = null;
        try {
            camera = Camera.open();
            Camera.Parameters parameters = camera.getParameters();
            parameters.set("s3d-prv-frame-layout", "none");
            parameters.set("s3d-cap-frame-layout", "none");
            parameters.set("iso", "auto");
            parameters.set("contrast", 100);
            parameters.set("brightness", 50);
            parameters.set("saturation", 100);
            parameters.set("sharpness", 100);
            parameters.setAntibanding("auto");
            parameters.setPictureFormat(ImageFormat.JPEG);
            parameters.set("jpeg-quality", 100);
            parameters.setPictureSize(800, 600);
            parameters.setRotation(180);
            camera.setDisplayOrientation(90);
            camera.setParameters(parameters);
        } catch (Exception e) {
            // cannot get camera or does not exist
        }
        return camera;
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio=(double)h / w;

        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }
}