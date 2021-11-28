package com.prinfosys.news48.Admin.topic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.prinfosys.news48.Admin.AddNews;
import com.prinfosys.news48.R;
import com.prinfosys.news48.RetrofitClient;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.prinfosys.news48.MainActivity.alert;
import static com.prinfosys.news48.MainActivity.apifailure;

public class TopicActivity extends AppCompatActivity {

    Button btn;
    EditText name,url;
    String tname,type,id,surl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        TopicActivity.this.setTitle("Topic Add");
        name = findViewById(R.id.tname);
        btn = findViewById(R.id.top);
        url = findViewById(R.id.url);
        type = getIntent().getStringExtra("type");
        if (type.equals("update")){
            TopicActivity.this.setTitle("Topic Update");
            tname = getIntent().getStringExtra("tname");
            id = getIntent().getStringExtra("id");
            surl = getIntent().getStringExtra("url");
            url.setText(surl);
            name.setText(tname);
            btn.setText("Update");
//            Toast.makeText(this, surl, Toast.LENGTH_SHORT).show();
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action();
            }
        });
    }

    private void action() {
        tname = name.getText().toString();
        surl = url.getText().toString();

        if (tname.isEmpty()) {
            name.setError("Enter Topic Name");
            name.requestFocus();
            return;
        }

        if (surl.isEmpty()) {
            url.setError("Enter url");
            url.requestFocus();
            return;
        }

        if (type.equals("update")){
            id = getIntent().getStringExtra("id");
            Call<topic> call = RetrofitClient.getInstance().getMyApi().Updatetopic("update",tname,id,surl);
            call.enqueue(new Callback<topic>() {
                @Override
                public void onResponse(Call<topic> call, Response<topic> response) {
                    String res = response.body().getResponse();
                    if (res.contains("ok")) {
                        alert(TopicActivity.this,"News","Done",R.color.green);
                        onBackPressed();
                    }else{
                        alert(TopicActivity.this,"News","Please try again after some time",R.color.red);
                    }
                }

                @Override
                public void onFailure(Call<topic> call, Throwable t) {
                    apifailure(t,TopicActivity.this);
                }
            });

        }else{
            Call<topic> call = RetrofitClient.getInstance().getMyApi().inserttopic("insert",tname,surl);
            call.enqueue(new Callback<topic>() {
                @Override
                public void onResponse(Call<topic> call, Response<topic> response) {
                    String res = response.body().getResponse();
                    if (res.contains("ok")) {
                        alert(TopicActivity.this,"News","Done",R.color.green);
                        onBackPressed();
                    }else{
                        alert(TopicActivity.this,"News","Please try again after some time",R.color.red);
                    }
                }

                @Override
                public void onFailure(Call<topic> call, Throwable t) {
                    apifailure(t,TopicActivity.this);
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}