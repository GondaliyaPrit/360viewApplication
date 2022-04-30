package com.cameraview.app.helper;

import static com.cameraview.app.helper.Constant.INPUT_HEIGHT;
import static com.cameraview.app.helper.Constant.INPUT_WIDTH;
import static com.cameraview.app.helper.Constant.FULL_SCREEN;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.cameraview.app.Activites.ConvertingActivity;
import com.cameraview.app.databinding.ActivityTakeImageBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Timer;
import java.util.TimerTask;

public class CameraPreviewViewModel {

    private Camera mCamera;
    private String TAG = "FullScreenPreviewViewModel";
    private CameraPreview maPreview;
    private Context context;
    private ActivityTakeImageBinding binding;
    int count;

    public CameraPreviewViewModel(Context context, ActivityTakeImageBinding activityFullScreenPreviewBinding) {
        this.context = context;
        this.binding = activityFullScreenPreviewBinding;
    }

    public void startCameraPreview() {
        try {
            Bundle extras = ((Activity) context).getIntent().getExtras();
            long inputWidth = 0;
            long inputHeight = 0;
            if (extras != null) {
                inputWidth=extras.getInt(INPUT_WIDTH,0);
                inputHeight=extras.getInt(INPUT_HEIGHT,0);
            }
            mCamera = getCameraInstance();
            maPreview = new CameraPreview(context, mCamera, null);
            binding.cameraPreview.addView(maPreview);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) maPreview.getLayoutParams();
            params.gravity = Gravity.CENTER;
            if(inputWidth>0&&inputHeight>0){
                setLayoutWH(inputWidth,inputHeight);
            }
        } catch (Exception exc) {
            Log.e(TAG, "startCameraPreview", exc);
        }
    }

    private void setLayoutWH( long inputWidth,long inputHeight) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int mScreenWidth = displayMetrics.widthPixels;
        int mScreenHeight = displayMetrics.heightPixels;
        int width = (int) (displayMetrics.widthPixels *((float)inputWidth/100));
        int height = (int) (displayMetrics.heightPixels * ((float)inputHeight/100));
        int optimalSize[] = Utility.getOptimalDimensions(mScreenWidth
                , mScreenHeight
                , width
                , height);
        binding.cameraPreview.getLayoutParams().width = optimalSize[0];
        binding.cameraPreview.getLayoutParams().height = optimalSize[1];
        binding.cameraPreview.getLayoutParams().width = optimalSize[0];
        binding.cameraPreview.getLayoutParams().height = optimalSize[1];
    }

    private Camera getCameraInstance() {
        if (mCamera == null)
            // mCamera = Camera.open(useBackCamera ? 0 : 1);
            mCamera = Camera.open(0);
        return mCamera;
    }

    public void captureImage() {
        binding.ivCapture.setOnClickListener((View view) -> {
            count = 0;
            Timer timer = new Timer();
            timer.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    try {
                        mCamera.takePicture(() -> {
                        }, null, (final byte[] bytes, Camera camera) -> {
                            try {
                                count += 1;
//                                mCamera.stopPreview();
                                saveImage(bytes,count);
                            } catch (Exception e) {
                                Log.e(TAG, "takePicture", e);
                            }
                        });
                    } catch (Exception e) {
                        Log.e(TAG, "capturePhoto", e);
                    }
                }
            }, 0, 5000);

        });
    }

    private void saveImage(final byte[] data,int count) {
        Log.e("Photo","Photo take");
        if(count == 10){
            context.startActivity(new Intent(context, ConvertingActivity.class));
            ((Activity)context).finish();
        }
        try {
            byte[] imageData = Utility.rotateImageData((Activity) context, data, 1);
            File userDIR = Utility.createExternalDirectory(FULL_SCREEN);
            File file = new File(userDIR, System.currentTimeMillis() + ".jpeg");
            FileOutputStream outPut = new FileOutputStream(file);
            outPut.write(imageData, 0, imageData.length);
            outPut.close();
            Toast.makeText(context, file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            mCamera.stopPreview();
            startCameraPreview();
            binding.cameraPreview.addView(maPreview);
        } catch (Exception e) {
            Log.e(TAG, "saveImage", e);
        }
    }
}
