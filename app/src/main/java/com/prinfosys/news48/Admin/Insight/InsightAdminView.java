package com.prinfosys.news48.Admin.Insight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.prinfosys.news48.Admin.Quiz.QuizMain;
import com.prinfosys.news48.Admin.Quiz.QuizShow;
import com.prinfosys.news48.Admin.ShowMainQuizAdapter;
import com.prinfosys.news48.InsightGridviewAdapter;
import com.prinfosys.news48.MainActivity;
import com.prinfosys.news48.Model.Insight;
import com.prinfosys.news48.MyClientLaravel;
import com.prinfosys.news48.QuizView;
import com.prinfosys.news48.R;
import com.prinfosys.news48.TopicGridView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsightAdminView extends AppCompatActivity {

    ArrayList<Insight> insightList;
//    ListView listView;
    InsightAdapter adapter;
    GridView gridView;
    InsightGridviewAdapter insightGridviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insight_admin_view);
//        listView = (ListView) findViewById(R.id.list_view);
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setTitle("Insights");
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        gridView = findViewById(R.id.grid_view);
        data();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(InsightAdminView.this, InsightViewPager.class);
                intent.putStringArrayListExtra("data_array",insightList.get(i).getIn_data());
                intent.putExtra("data_background",insightList.get(i).getIn_background());
                intent.putExtra("header_txt",insightList.get(i).getCreated_at());
                startActivity(intent);
            }
        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(InsightAdminView.this, InsightViewPager.class);
//                intent.putStringArrayListExtra("data_array",insightList.get(i).getIn_data());
//                intent.putExtra("data_background",insightList.get(i).getIn_background());
//                startActivity(intent);
//            }
//        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void data(){
        Call<Insight> call = MyClientLaravel.getInstance().getLaravelApi().getinsight();
        call.enqueue(new Callback<Insight>() {
            @Override
            public void onResponse(Call<Insight> call, Response<Insight> response) {
                if (response.isSuccessful()){
                    if(response.body().getResponse().equals("ok")) {
                        insightList = response.body().getBody();
                        insightGridviewAdapter = new InsightGridviewAdapter(InsightAdminView.this,R.layout.insight_list_view, insightList);
                        gridView.setAdapter(insightGridviewAdapter);
                    }
                }else{
                    Toast.makeText(InsightAdminView.this,"Something went wrong" , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Insight> call, Throwable t) {

            }
        });
    }
}