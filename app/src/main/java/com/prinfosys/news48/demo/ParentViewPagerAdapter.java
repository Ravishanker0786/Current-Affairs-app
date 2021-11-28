package com.prinfosys.news48.demo;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ParentViewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<DataModel> dataModels = new ArrayList<>();
    LayoutInflater mLayoutInflater;
    public ParentViewPagerAdapter(FragmentManager fm, ArrayList<DataModel> dataModels,Context context) {
        super(fm);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.dataModels = dataModels;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        return ParentFragment.newInstance(dataModels.get(position));

    }

    @Override
    public int getCount() {
        return dataModels.size();
    }
}



