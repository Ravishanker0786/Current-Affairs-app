package com.prinfosys.news48;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.os.Bundle;

import com.prinfosys.news48.Adapters.Adapter;
import com.prinfosys.news48.Model.Quiz;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizView extends AppCompatActivity {

    ViewPager viewPager;
    Adapter adapter;
    ArrayList<Quiz> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    String qno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_view);

        viewPager = findViewById(R.id.viewPager);
        qno = getIntent().getStringExtra("qno");

        Integer[] colors_temp = {
                getResources().getColor(R.color.nblue),
                getResources().getColor(R.color.nblue),
                getResources().getColor(R.color.nblue),
                getResources().getColor(R.color.nblue)
        };

        colors = colors_temp;

        Call<Quiz> call = RetrofitClient.getInstance().getMyApi().getQuesListByQno("all",qno);
        call.enqueue(new Callback<Quiz>() {
            @Override
            public void onResponse(Call<com.prinfosys.news48.Model.Quiz> call, Response<Quiz> response) {
                if(response.body().getResponse().equals("ok")){
                    models = response.body().getBody();
                    adapter = new Adapter(models,QuizView.this);
                    viewPager.setAdapter(adapter);
                    viewPager.setPadding(50, 0, 50, 0);
                }
            }

            @Override
            public void onFailure(Call<com.prinfosys.news48.Model.Quiz> call, Throwable t) {

            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (adapter.getCount() -1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(getResources().getColor(R.color.white)

//                            (Integer) argbEvaluator.evaluate(
//                                    positionOffset,
//                                    colors[position],
//                                    colors[position + 1]
//                            )
                    );
                }

                else {
                    viewPager.setBackgroundColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}