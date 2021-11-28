package com.prinfosys.news48.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.prinfosys.news48.R;
import com.prinfosys.news48.demo.VerticalViewPager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Demo extends AppCompatActivity  implements ParentFragment.ToggleVerticalViewPagerScrolling {
    private ParentViewPagerAdapter verticalPagerAdapter;
    private VerticalViewPager verticalViewPager;
    ArrayList<DataModel> dataModels;
//    private VerticalViewPager verticalViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        Call<DataModel> call= RetrofitClient.getInstance().getMyApi().getNews("all");
        call.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                if(response.body().getResponse().equals("ok")){
                    dataModels = response.body().getBody();
                    verticalPagerAdapter = new ParentViewPagerAdapter(getSupportFragmentManager(), dataModels,Demo.this);
                    verticalViewPager = findViewById(R.id.container);
                    verticalViewPager.setAdapter(verticalPagerAdapter);
                }
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {

            }
        });
    }
    @Override
    public void trigger(int page) {


        if (page == 1) {
            verticalViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        } else {
            verticalViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
        }

    }
}