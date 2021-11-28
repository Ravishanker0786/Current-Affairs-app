package com.prinfosys.news48.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.prinfosys.news48.Admin.Quiz.QuizMain;
import com.prinfosys.news48.Admin.Quiz.QuizShow;
import com.prinfosys.news48.R;
import com.prinfosys.news48.Admin.topic.ShowTopic;

import static com.prinfosys.news48.MainActivity.alert;

public class AdminView extends AppCompatActivity {

    Button btn1,btn2,btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);

        btn1 = findViewById(R.id.topics);
        btn2 = findViewById(R.id.news);
        btn3 = findViewById(R.id.quiz);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()){
                    Intent intent = new Intent(AdminView.this, ShowTopic.class);
                    startActivity(intent);
                }else{
                    alert(AdminView.this,"Connection Failed","Check your Internet Connection",R.color.red);
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()){
                    Intent intent = new Intent(AdminView.this, ShowNew.class);
                    startActivity(intent);
                }else{
                    alert(AdminView.this,"Connection Failed","Check your Internet Connection",R.color.red);
                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()){
                    Intent intent = new Intent(AdminView.this, QuizMain.class);
                    intent.putExtra("type","admin");
                    startActivity(intent);
                }else{
                    alert(AdminView.this,"Connection Failed","Check your Internet Connection",R.color.red);
                }
            }
        });

    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}