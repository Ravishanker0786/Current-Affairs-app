package com.prinfosys.news48.demo;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ChildViewPagerAdapter extends FragmentPagerAdapter {


    DataModel model;

    public ChildViewPagerAdapter(FragmentManager fm, DataModel model) {
        super(fm);
        this.model = model;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ChildFragment.newInstance(model);
            case 1:
                return SecondFragment.newInstance(model);
            case 2:
                return ChildFragment.newInstance(model);
            default:
                return ChildFragment.newInstance(model);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Child Fragment " + position;
    }

}
