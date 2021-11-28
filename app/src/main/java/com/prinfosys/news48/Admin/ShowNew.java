package com.prinfosys.news48.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.prinfosys.news48.R;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowNew extends AppCompatActivity {

    ListView listView;
    ArrayList<NewsBody> newsList;
    ShowNewsAdapter showNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_new);
        ShowNew.this.setTitle("News List");
//        Toast.makeText(this, "hlllo", Toast.LENGTH_SHORT).show();
        listView = findViewById(R.id.list_view);
        data();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ShowNew.this,AddNews.class);
                intent.putExtra("title",newsList.get(i).getTitle());
                intent.putExtra("desc",newsList.get(i).getDescription());
                intent.putExtra("newsurl",newsList.get(i).getNewsurl());
                intent.putExtra("imgurl",newsList.get(i).getImageurl());
                intent.putExtra("id",newsList.get(i).getId());
                intent.putExtra("tid",newsList.get(i).getTid());
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
                Intent intent = new Intent(ShowNew.this, AddNews.class);
                intent.putExtra("type","insert");
                startActivity(intent);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void data(){
        Call<NewsBody>call = MyClient.getInstance().getMyApi().newsdata("alll");
        call.enqueue(new Callback<NewsBody>() {
            @Override
            public void onResponse(Call<NewsBody> call, Response<NewsBody> response) {
                if(response.body().getResponse().equals("ok")) {
                    newsList = response.body().getNewsList();
                    showNewsAdapter = new ShowNewsAdapter(ShowNew.this,newsList);
                    listView.setAdapter(showNewsAdapter);
                }
            }

            @Override
            public void onFailure(Call<NewsBody> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        data();
        super.onResume();
    }
}