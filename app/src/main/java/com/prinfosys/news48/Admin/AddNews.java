package com.prinfosys.news48.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.prinfosys.news48.MainActivity;
import com.prinfosys.news48.R;
import com.prinfosys.news48.Admin.topic.ShowTopic;
import com.prinfosys.news48.Admin.topic.TopicAdapter;
import com.prinfosys.news48.Admin.topic.topic;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.prinfosys.news48.MainActivity.alert;
import static com.prinfosys.news48.MainActivity.apifailure;

public class AddNews extends AppCompatActivity {

    EditText atitle,nurl,iurl,des;
    String title,newsurl,imgurl,desc,type,id,tid;
    Button btn;
    Spinner spinner;
    TextView textView;
    ArrayList<topic> topicList;
    SpinnerAdapter spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        atitle = findViewById(R.id.atitle);
        nurl = findViewById(R.id.nurl);
        iurl = findViewById(R.id.iurl);
        des = findViewById(R.id.des);
        btn = findViewById(R.id.button2);
        spinner = findViewById(R.id.spinner);
        textView = findViewById(R.id.tid);

        type = getIntent().getStringExtra("type");

        if (type.equals("update")){
            AddNews.this.setTitle("Update News");
            title = getIntent().getStringExtra("title");
            atitle.setText(title);
            desc = getIntent().getStringExtra("desc");
            des.setText(desc);
            newsurl = getIntent().getStringExtra("newsurl");
            nurl.setText(newsurl);
            imgurl = getIntent().getStringExtra("imgurl");
            tid = getIntent().getStringExtra("tid");
//            Toast.makeText(this, tid, Toast.LENGTH_SHORT).show();
            textView.setText(tid);
            iurl.setText(imgurl);
            btn.setText("Update");
        }else{
            type = "insert";
            AddNews.this.setTitle("Insert News");
        }


        Call<topic> call = MyClient.getInstance().getMyApi().gettopic("all");
        call.enqueue(new Callback<topic>() {
            @Override
            public void onResponse(Call<topic> call, Response<topic> response) {
                if (response.body().getResponse().contains("ok")){
                    topicList = response.body().getTopicList();
                    spinnerAdapter = new SpinnerAdapter(AddNews.this,R.layout.spinnerview,R.id.spinnertext,topicList);
                    spinner.setAdapter(spinnerAdapter);

                    if (type.equals("update")){
                        for (int i = 0; i < topicList.size(); i++) {
                            if (topicList.get(i).getId().equals(tid)) {
                                spinner.setSelection(i);
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<topic> call, Throwable t) {
                apifailure(t,AddNews.this);
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()){
                    Data();
                }else{
                    alert(AddNews.this,"Connection Failed","Check your Internet Connection",R.color.red);
                }
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textView.setText(topicList.get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }



    private void Data(){
        title = atitle.getText().toString().trim();
        newsurl = nurl.getText().toString().trim();
        imgurl = iurl.getText().toString().trim();
        desc = des.getText().toString().trim();
        tid = textView.getText().toString().trim();

        if (title.isEmpty()) {
            atitle.setError("Enter Title");
            atitle.requestFocus();
            return;
        }
        if (newsurl.isEmpty()) {
            nurl.setError("Enter news Url");
            nurl.requestFocus();
            return;
        }
        if (imgurl.isEmpty()) {
            iurl.setError("Enter image Url");
            iurl.requestFocus();
            return;
        }
        if (desc.isEmpty()) {
            des.setError("Enter Description");
            des.requestFocus();
            return;
        }

        if (tid.isEmpty()){
            textView.setError("Please Select a Topic before performing any opertaion");
            textView.requestFocus();
            return;
        }

        if (type.equals("update")){
            id = getIntent().getStringExtra("id");
//            Toast.makeText(this, id, Toast.LENGTH_LONG).show();
//            Toast.makeText(this, "update", Toast.LENGTH_SHORT).show();
            Call<ResponseBody>call = MyClient.getInstance().getMyApi().updatedata(id,type,title,imgurl,newsurl,desc,tid);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String res = response.body().toString();
                    Log.i("TAG", res);
                    if (res.contains("ok")) {
//                        alert(AddNews.this,"News","Done",R.color.green);
                        onBackPressed();
                    }else{
                        alert(AddNews.this,"News","Please try again after some time",R.color.red);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    apifailure(t,AddNews.this);
                }
            });
        }else{
            Toast.makeText(this, "insert", Toast.LENGTH_SHORT).show();
            Call<ResponseBody>call = MyClient.getInstance().getMyApi().insertdata(type,title,imgurl,newsurl,desc,tid);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String res = response.body().toString();
                    if (res.contains("ok")) {
                        onBackPressed();
                    }else{
                        alert(AddNews.this,"News","Please try again after some time",R.color.red);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }


    }

    @Override
    public void onBackPressed() {
        alert(AddNews.this,"News","Done",R.color.green);
        super.onBackPressed();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}