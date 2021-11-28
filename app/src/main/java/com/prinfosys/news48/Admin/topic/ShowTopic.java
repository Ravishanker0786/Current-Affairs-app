package com.prinfosys.news48.Admin.topic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.prinfosys.news48.R;
import com.prinfosys.news48.Admin.AddNews;
import com.prinfosys.news48.Admin.MyClient;
import com.prinfosys.news48.Admin.ShowNew;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowTopic extends AppCompatActivity {

    ListView listView;
    ArrayList<topic> topicList;
    TopicAdapter topicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_topic);
        ShowTopic.this.setTitle("Topic List");
        listView = findViewById(R.id.list_topic);
        data();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ShowTopic.this,TopicActivity.class);
                intent.putExtra("id",topicList.get(i).getId());
                intent.putExtra("tname",topicList.get(i).getTname());
                intent.putExtra("url",topicList.get(i).getUrl());
                intent.putExtra("type","update");
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        MenuItem add = menu.findItem(R.id.add);
        add.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(ShowTopic.this, TopicActivity.class);
                intent.putExtra("type","insert");
                startActivity(intent);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void data(){
        Call<topic> call = MyClient.getInstance().getMyApi().gettopic("all");
        call.enqueue(new Callback<topic>() {
            @Override
            public void onResponse(Call<topic> call, Response<topic> response) {
                if (response.body().getResponse().contains("ok")){
                    topicList = response.body().getTopicList();
                    topicAdapter = new TopicAdapter(ShowTopic.this,topicList);
                    listView.setAdapter(topicAdapter);
                }
            }

            @Override
            public void onFailure(Call<topic> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        data();
        super.onResume();
    }
}