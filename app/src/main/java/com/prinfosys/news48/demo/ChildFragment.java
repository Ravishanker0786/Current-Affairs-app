package com.prinfosys.news48.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.prinfosys.news48.R;

public class ChildFragment extends Fragment {

    public ChildFragment() {
    }

    public static ChildFragment newInstance(DataModel dataModel) {
        ChildFragment fragment = new ChildFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_child, container, false);
        RelativeLayout rl = rootView.findViewById(R.id.rl);

        TextView txtTitle = rootView.findViewById(R.id.txtTitle);
//        Button button = rootView.findViewById(R.id.button);
        TextView txtDescription = rootView.findViewById(R.id.txtDescription);

        boolean isWebView = getArguments().getBoolean("isWebView");
        if (isWebView) {
//            webView.setVisibility(View.VISIBLE);
//            rl.setVisibility(View.GONE);
//            button.setVisibility(View.GONE);
//            txtDescription.setVisibility(View.GONE);
//
//            webView.loadUrl(getArguments().getString("url"));

        } else {

            rl.setVisibility(View.VISIBLE);
            txtDescription.setVisibility(View.VISIBLE);
//            button.setVisibility(View.VISIBLE);

            txtTitle.setText(getArguments().getString("title"));
            txtDescription.setText(getArguments().getString("description"));
        }

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ParentFragment parentFrag = ((ParentFragment) ChildFragment.this.getParentFragment());
//                parentFrag.nestedViewPager.setCurrentItem(1);
//
//
//            }
//        });


        return rootView;
    }
}
