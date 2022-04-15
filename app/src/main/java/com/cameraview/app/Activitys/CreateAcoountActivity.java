package com.cameraview.app.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cameraview.app.databinding.ActivityCreateAcoountBinding;

public class CreateAcoountActivity extends AppCompatActivity {
    ActivityCreateAcoountBinding createAcoountBinding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createAcoountBinding = ActivityCreateAcoountBinding.inflate(getLayoutInflater());
        setContentView(createAcoountBinding.getRoot());
    }
}