package com.cameraview.app.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cameraview.app.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
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

        binding.signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
                finish();
            }
        });

        binding.siginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ApiCallActivity.class));
            }
        });
    }
}


/***

 Date : 25-4-2022

 I have worked on Number of project (1) and below are my updates-

 Project name : KeepShake Digital

 1) R&D on how to create 360 view using video
 2) R&D on play store any application available that provide 360view
 3) Create flipkart 360 view video and upload in drive

 Thank you...


 Date : 25-4-2022

 I have worked on Number of project (1) and below are my updates-

 Project name : UberRider

 1) create api interface class
 2) Create apiclient interface class
 3) Create function for base url
 4) create function for http calling
 5) create api function for login and register

 Thank you...

 * ***/