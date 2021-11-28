package com.prinfosys.news48.Admin.Quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.prinfosys.news48.Admin.AddNews;
import com.prinfosys.news48.Admin.ShowMainQuizAdapter;
import com.prinfosys.news48.Admin.ShowNew;
import com.prinfosys.news48.Model.Quiz;
import com.prinfosys.news48.MyClientLaravel;
import com.prinfosys.news48.QuizView;
import com.prinfosys.news48.R;


import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizMain extends AppCompatActivity {
    ListView listView;
    private static final String TAG = "MainActivity";
    ArrayList<Quiz> quesList;
    Array temp;
    ShowMainQuizAdapter adapter;
    String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);
        type = getIntent().getStringExtra("type");
        listView = (ListView) findViewById(R.id.list_view);
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setTitle("Quiz");
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setDisplayHomeAsUpEnabled(true);
        data();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (type.equals("admin")){
                    Intent intent = new Intent(QuizMain.this, QuizShow.class);
                    intent.putExtra("qno",quesList.get(i).getQno());
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(QuizMain.this, QuizView.class);
                    intent.putExtra("qno",quesList.get(i).getQno());
                    startActivity(intent);
                }

            }
        });

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
        Call<Quiz> call = MyClientLaravel.getInstance().getLaravelApi().getquiz();
        call.enqueue(new Callback<Quiz>() {
            @Override
            public void onResponse(Call<Quiz> call, Response<Quiz> response) {
                if (response.isSuccessful()){
                    if(response.body().getResponse().equals("ok")) {
                        quesList = response.body().getBody();
                        adapter = new ShowMainQuizAdapter(QuizMain.this,quesList);
                        listView.setAdapter(adapter);
                    }
                }else{
                    Toast.makeText(QuizMain.this,response.errorBody().toString() , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Quiz> call, Throwable t) {

            }
        });
    }
}