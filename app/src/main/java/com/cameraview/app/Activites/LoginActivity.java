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
 *
 *
 Date : 21-4-2022

 I have worked on Number of project (1) and below are my updates-

 Project name : KeepShake Digital

 1) Remove bg api calling
 2) call api in loop
 3) but it's shown error in API because of rate limit exceeded
 4) create recusrsion function for call but when upload more than 5 image than show error
 5) creadit over in one application so create new account same like create 2 more account

 Thank you...







 Date : 20-4-2022

 I have worked on Number of project (2) and below are my updates-

 Project name : Luxury-x chnage

 2) compare with site and creaqte list of diffrent
 1) change drwaer list
 2) change category list
 3) chaange drawer design (working...)


 Project Name : KeepShake Digital

 1) help to sanket for store image into list and call api

 Thank you...*
 * ***/