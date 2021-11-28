package com.prinfosys.news48.Admin.Insight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.prinfosys.news48.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InsightViewPagerAdapter extends PagerAdapter {

    Context mContext;
    ArrayList<String> urls;
    private LayoutInflater layoutInflater;

    InsightViewPagerAdapter(Context context,ArrayList<String> urls) {
        this.mContext = context;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ImageView)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {



        ImageView imageView = new ImageView(mContext);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.get().load(urls.get(position)).into(imageView);
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}
