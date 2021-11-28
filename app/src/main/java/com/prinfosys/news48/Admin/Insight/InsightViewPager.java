package com.prinfosys.news48.Admin.Insight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.prinfosys.news48.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class InsightViewPager extends AppCompatActivity {

    ArrayList<String> data;
    String background,title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insight_view_pager);
        data = getIntent().getExtras().getStringArrayList("data_array");
        background = getIntent().getStringExtra("data_background");
        title = getIntent().getStringExtra("header_txt");
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setTitle("Insights");
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPage);
        InsightViewPagerAdapter adapterView = new InsightViewPagerAdapter(this,data);
        mViewPager.setAdapter(adapterView);

//        Toast.makeText(InsightViewPager.this, data.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}