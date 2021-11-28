package com.prinfosys.news48.demo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.prinfosys.news48.R;


public class SecondFragment extends Fragment {

    private WebView webView;
    private String newsurl;
    public static SecondFragment newInstance(DataModel dataModel) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString("newsurl", dataModel.getNewsurl());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        webView = view.findViewById(R.id.web_view);
        newsurl = getArguments().getString("newsurl");
//        webView.loadUrl(newsurl);
        return view;
    }
}