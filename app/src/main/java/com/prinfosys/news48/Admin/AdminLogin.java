package com.prinfosys.news48.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.prinfosys.news48.Admin.topic.topic;
import com.prinfosys.news48.Model.pass;
import com.prinfosys.news48.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminLogin extends AppCompatActivity {

    EditText ed1 , ed2 ;
    Button log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        ed1 = findViewById(R.id.ed1);
        ed2 = findViewById(R.id.ed2);
        log = findViewById(R.id.bt1);
//        ed1.setText("admin");
//        ed2.setText("123456");


        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ed1.getText().toString().equals("admin")){
                    ed1.setError("invalid user name");
                    ed1.requestFocus();
                    return;
                }

                Call<pass> call = MyClient.getInstance().getMyApi().getpass("login");
                call.enqueue(new Callback<pass>() {
                    @Override
                    public void onResponse(Call<pass> call, Response<pass> response) {
                        if (response.body().getResponse().contains("ok")){
                            if (ed2.getText().toString().equals(response.body().getPass())){
                                Intent intent = new Intent(AdminLogin.this, AdminView.class);
                                startActivity(intent);
                            }else{
                                ed2.setError("invalid password");
                                ed2.requestFocus();
                                return;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<pass> call, Throwable t) {

                    }
                });


            }
        });
    }
}