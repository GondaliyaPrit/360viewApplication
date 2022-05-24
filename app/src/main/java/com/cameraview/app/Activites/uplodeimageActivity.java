package com.cameraview.app.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.cameraview.app.R;
import com.cameraview.app.databinding.ActivityUplodeimageBinding;

public class uplodeimageActivity extends AppCompatActivity {
    ActivityUplodeimageBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityUplodeimageBinding.inflate(getLayoutInflater());
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
        binding.Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}