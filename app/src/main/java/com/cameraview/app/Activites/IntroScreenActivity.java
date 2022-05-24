package com.cameraview.app.Activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    ActivityIntroScreenBinding binding;
    ArrayList<Intropage> introlist;
    ImageView[] dot;
    int selectedposition = 0;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroScreenBinding.inflate(getLayoutInflater());
        initvariable();
        setdata();
        initclicklistner();
        setContentView(binding.getRoot());
    }

    private void initvariable() {
        introlist = new ArrayList<>();
        context = IntroScreenActivity.this;
    }

    private void setdata() {
        introlist.add(new Intropage("Well Organized Searching System", "Taking care of your health has become easier! Lerm more, how to do it.", R.drawable.fristscrren));
        introlist.add(new Intropage("Multiple Card Mananger", "Taking care of your health has become easier! Lerm more, how to do it.", R.drawable.secondscreen));
        introlist.add(new Intropage("Explore and Start the Journey", "Taking care of your health has become easier! Lerm more, how to do it.", R.drawable.thirdscreen));
        binding.intropage.setAdapter(new IntroAdapter(this, introlist));
        selectedposition = 0;
    }


    private void initclicklistner() {
        addDot(selectedposition);
        binding.intropage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                addDot(position);
                selectedposition = position;
                if (position == 2) {
                    binding.layoutallow.setVisibility(View.VISIBLE);
                    binding.nextbtnlayout.setVisibility(View.INVISIBLE);
                    binding.skipbtnlayout.setVisibility(View.INVISIBLE);
                    binding.btnallow.setVisibility(View.VISIBLE);
                    binding.layoutsIndicator.setVisibility(View.GONE);
                } else {
                    binding.layoutallow.setVisibility(View.INVISIBLE);
                    binding.nextbtnlayout.setVisibility(View.VISIBLE);
                    binding.skipbtnlayout.setVisibility(View.VISIBLE);
                    binding.layoutsIndicator.setVisibility(View.VISIBLE);
                    binding.btnallow.setVisibility(View.GONE);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        binding.btnallow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroScreenActivity.this, WelcomeActivity.class));
                finish();
            }
        });


        binding.nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.intropage.setCurrentItem(getItem(+1));
            }
        });

        binding.skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("btn", "click on skip");
                startActivity(new Intent(context, WelcomeActivity.class));
                finish();
            }
        });
    }

    private int getItem(int i) {
        return binding.intropage.getCurrentItem() + i;
    }


    public void addDot(int page_position) {
        dot = new ImageView[introlist.size()];
        binding.layoutsIndicator.removeAllViews();
        for (int i = 0; i < dot.length; i++) {
            dot[i] = new ImageView(IntroScreenActivity.this);
            dot[i].setImageDrawable(getResources().getDrawable(R.drawable.bg_noactive));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(10, 0, 10, 0);
            binding.layoutsIndicator.addView(dot[i], params);
        }
        //active dot
        dot[page_position].setImageDrawable(getResources().getDrawable(R.drawable.bg_active));
    }

}