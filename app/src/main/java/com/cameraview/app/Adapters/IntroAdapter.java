package com.cameraview.app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


import com.cameraview.app.Activites.IntroScreenActivity;
import com.cameraview.app.R;
import com.cameraview.app.Utils.Intropage;

import java.util.ArrayList;

public class IntroAdapter extends PagerAdapter {
    IntroScreenActivity introactivity;
    ArrayList<Intropage> introlist;
    public IntroAdapter(IntroScreenActivity introactivity, ArrayList<Intropage> introlist) {
        this.introactivity = introactivity;
        this.introlist = introlist;
    }

    @Override
    public int getCount() {
        return introlist.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(introactivity).inflate(R.layout.itemintro,container,false);

        TextView title  = view.findViewById(R.id.txttitle);
        TextView desc  = view.findViewById(R.id.txtdesc);
        ImageView img = view.findViewById(R.id.img_intro);

        title.setText(introlist.get(position).getTitle());
        desc.setText(introlist.get(position).getIntro());
        img.setImageResource(introlist.get(position).getPageimage());

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }


}
