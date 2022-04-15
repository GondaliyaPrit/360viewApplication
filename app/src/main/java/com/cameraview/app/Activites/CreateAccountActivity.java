package com.cameraview.app.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.cameraview.app.databinding.ActivityCreateAcoountBinding;

public class CreateAccountActivity extends AppCompatActivity {
    ActivityCreateAcoountBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAcoountBinding.inflate(getLayoutInflater());
        initvalue();
        setdata();
        initlister();
        setContentView(binding.getRoot());
    }

    private void initvalue() {
    }

    private void setdata() {
    }

    private void initlister() {
        binding.signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}