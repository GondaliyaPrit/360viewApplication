package com.cameraview.app.Activites;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cameraview.app.databinding.ActivityConvertingBinding;

public class ConvertingActivity extends AppCompatActivity {
    ActivityConvertingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConvertingBinding.inflate(getLayoutInflater());
        initvariable();
        setdata();
        initclicklistner();
        setContentView(binding.getRoot());
    }

    private void initvariable() {
    }

    private void setdata() {
    }

    private void initclicklistner() {
    }


}