package com.prinfosys.news48.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.prinfosys.news48.Model.Quiz;

public class QuizShowAdapter extends FragmentPagerAdapter {

    Quiz quiz;

    public QuizShowAdapter(FragmentManager fm , Quiz quiz) {
        super(fm);
        this.quiz=quiz;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return null;
    }


    @Override
    public int getCount() {
        return 0;
    }
}
