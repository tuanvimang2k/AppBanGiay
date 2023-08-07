package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.myapplication.R;

public class QuangCaoSliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public QuangCaoSliderAdapter(Context context) {
        this.context = context;
    }

    int titles[] = {
            R.string.tittle1,
            R.string.tittle2,
            R.string.tittle3,
            R.string.tittle4
    };
    int subtittles[] = {
            R.string.subtittle1,
            R.string.subtittle2,
            R.string.subtittle3,
            R.string.subtittle4
    };

    int images[] = {
            R.drawable.lg1,
            R.drawable.lg2,
            R.drawable.lg3,
            R.drawable.lg4
    };

    int bg[] = {
            R.drawable.bg1,
            R.drawable.bg2,
            R.drawable.bg3,
            R.drawable.bg4
    };


    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.slide1,container,false);
        ImageView image = v.findViewById(R.id.slide1img);
        TextView title = v.findViewById(R.id.sliderTittle);
        TextView subtitle = v.findViewById(R.id.sliderSubString);
        ConstraintLayout layout = v.findViewById(R.id.sliderLayout);

        image.setImageResource(images[position]);
        title.setText(titles[position]);
        subtitle.setText(subtittles[position]);
        layout.setBackgroundResource(bg[position]);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
