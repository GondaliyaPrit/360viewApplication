package com.cameraview.app.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cameraview.app.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {
    ActivityWelcomeBinding welcomeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        welcomeBinding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        initvariable();
        setdata();
        initclicklistner();
        setContentView(welcomeBinding.getRoot());
    }


    private void initvariable() {
    }

    private void setdata() {
    }

    private void initclicklistner() {
        welcomeBinding.signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                finish();
            }
        });
    }



}