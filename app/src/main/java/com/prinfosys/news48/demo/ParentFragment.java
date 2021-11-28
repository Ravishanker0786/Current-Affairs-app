package com.prinfosys.news48.demo;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.prinfosys.news48.R;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ParentFragment extends Fragment {
    private boolean scrollStarted, checkDirection;
    private static final float thresholdOffset = 0.5f;
    ViewPager nestedViewPager;
    Activity mActivity;
    ToggleVerticalViewPagerScrolling tv;
    int oldPosition = -1;

    public ParentFragment() {
    }

    public static ParentFragment newInstance(DataModel dataModel) {
        ParentFragment fragment = new ParentFragment();
        Bundle args = new Bundle();
        args.putString("title", dataModel.getTitle());
        args.putString("description", dataModel.getDescription());
        args.putString("newsurl", dataModel.getNewsurl());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parent, container, false);
        String title = getArguments().getString("title");
        String description = getArguments().getString("description");
        String newsurl = getArguments().getString("newsurl");
        DataModel model = new DataModel(title,newsurl, description);
        nestedViewPager = rootView.findViewById(R.id.nestedViewPager);
        /** Important: Must use the child FragmentManager or you will see side effects. */
        nestedViewPager.setAdapter(new ChildViewPagerAdapter(getChildFragmentManager(), model));
        nestedViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                if (position != oldPosition) {
////                    tv.trigger(position);
//                }
//                oldPosition = position;
                if (checkDirection) {
                    if (thresholdOffset > positionOffset) {
                        Toast.makeText(mActivity, "left", Toast.LENGTH_SHORT).show();
//                        Log.i(C.TAG, "going left");
                    } else {
//                        Log.i(C.TAG, "going right");
                        Toast.makeText(mActivity, "right", Toast.LENGTH_SHORT).show();
                    }
                    checkDirection = false;
                }
            }
            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (!scrollStarted && state == ViewPager.SCROLL_STATE_DRAGGING) {
                    scrollStarted = true;
                    checkDirection = true;
                } else {
                    scrollStarted = false;
                }
            }
        });
        return rootView;
    }
    public interface ToggleVerticalViewPagerScrolling {
        void trigger(int page);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
        try {
            tv = (ToggleVerticalViewPagerScrolling) mActivity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }
}
