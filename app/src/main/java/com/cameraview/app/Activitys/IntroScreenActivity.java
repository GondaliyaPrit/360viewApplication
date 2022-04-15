package com.cameraview.app.Activitys;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.cameraview.app.Adapters.IntroAdapter;
import com.cameraview.app.R;
import com.cameraview.app.Utils.Intropage;
import com.cameraview.app.databinding.ActivityIntroScreenBinding;

import java.util.ArrayList;


public class IntroScreenActivity extends AppCompatActivity {

    ActivityIntroScreenBinding activityIntroScreenBinding ;
    ArrayList<Intropage> introlist;
    ImageView[] dot;
    int selectedposition= 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityIntroScreenBinding = ActivityIntroScreenBinding.inflate(getLayoutInflater());
        setContentView(activityIntroScreenBinding.getRoot());


        initvariable();
        setdata();
        initclicklistner();


    }

    private void initvariable() {

        introlist = new ArrayList<>();
    }

    private void setdata() {
        introlist.add(new Intropage("Well Organized Searching System", "Taking care of your health has become easier! Lerm more, how to do it.", R.drawable.fristscrren));
        introlist.add(new Intropage("Multiple Card Mananger", "Taking care of your health has become easier! Lerm more, how to do it.", R.drawable.secondscreen));
        introlist.add(new Intropage("Explore and Start the Journey", "Taking care of your health has become easier! Lerm more, how to do it.", R.drawable.thirdscreen));
        activityIntroScreenBinding.intropage.setAdapter(new IntroAdapter(this, introlist));
        selectedposition =0;
    }


    private void initclicklistner() {
        addDot(selectedposition);
        activityIntroScreenBinding.intropage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addDot(position);
                selectedposition=position;
                if (position==2)
                {
                    activityIntroScreenBinding.layoutallow.setVisibility(View.VISIBLE);
                }
                else
                {
                    activityIntroScreenBinding.layoutallow.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        activityIntroScreenBinding.btnallow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroScreenActivity.this,WelcomeActivity.class));
                finish();
            }
        });
    }


    public void addDot(int page_position) {
        dot = new ImageView[introlist.size()];
        activityIntroScreenBinding.layoutsIndicator.removeAllViews();
        for (int i = 0; i < dot.length; i++) {
            dot[i] = new ImageView(IntroScreenActivity.this);
            dot[i].setImageDrawable(getResources().getDrawable(R.drawable.bg_noactive));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(10, 0, 10, 0);
            activityIntroScreenBinding.layoutsIndicator.addView(dot[i], params);
        }
        //active dot
        dot[page_position].setImageDrawable(getResources().getDrawable(R.drawable.bg_active));

    }

}