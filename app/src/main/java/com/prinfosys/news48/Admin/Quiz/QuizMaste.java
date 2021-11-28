package com.prinfosys.news48.Admin.Quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.prinfosys.news48.Admin.topic.TopicActivity;
import com.prinfosys.news48.Model.Quiz;
import com.prinfosys.news48.R;
import com.prinfosys.news48.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.prinfosys.news48.MainActivity.alert;
import static com.prinfosys.news48.MainActivity.apifailure;

public class QuizMaste extends AppCompatActivity {

    EditText ques_et,op1_et,op2_et,op3_et,op4_et,ans_et;
    Button btn;
    String type,uqid,qno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_maste);
        ques_et = findViewById(R.id.ques_quiz);
        op1_et = findViewById(R.id.op1_quiz);
        op2_et = findViewById(R.id.op2_quiz);
        op3_et = findViewById(R.id.op3_quiz);
        op4_et = findViewById(R.id.op4_quiz);
        ans_et = findViewById(R.id.ans_quiz);
        btn = findViewById(R.id.submit_quiz);
        qno = getIntent().getStringExtra("qno");
        QuizMaste.this.setTitle("Insert Quiz");
        type = getIntent().getStringExtra("type");
        if (type != null && type.equals("update")){
//            Toast.makeText(this, "UPDATE", Toast.LENGTH_SHORT).show();
            QuizMaste.this.setTitle("Update Quiz");
            ques_et.setText(getIntent().getStringExtra("ques"));
            op1_et.setText(getIntent().getStringExtra("op1"));
            op2_et.setText(getIntent().getStringExtra("op2"));
            op3_et.setText(getIntent().getStringExtra("op3"));
            op4_et.setText(getIntent().getStringExtra("op4"));
            ans_et.setText(getIntent().getStringExtra("ans"));
            uqid = getIntent().getStringExtra("qid");

        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (type.equals("update")){

                    Call<Quiz> call = RetrofitClient.getInstance().getMyApi().quiz("update",uqid,ques_et.getText().toString(),ans_et.getText().toString()
                            ,op1_et.getText().toString(),op2_et.getText().toString(),op3_et.getText().toString(),op4_et.getText().toString(),qno);
                    call.enqueue(new Callback<Quiz>() {
                        @Override
                        public void onResponse(Call<Quiz> call, Response<Quiz> response) {
                            if(response.body().getResponse().equals("ok")){
                                alert(QuizMaste.this,"News","Done",R.color.green);
//                                Toast.makeText(QuizMaste.this, "sucess", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }else{
                                alert(QuizMaste.this,"News","Please try again after some time",R.color.red);
                            }
                        }

                        @Override
                        public void onFailure(Call<Quiz> call, Throwable t) {
                            apifailure(t,QuizMaste.this);
                        }
                    });

                }else{
                    Call<Quiz> call = RetrofitClient.getInstance().getMyApi().quiz("insert","0",ques_et.getText().toString(),ans_et.getText().toString()
                            ,op1_et.getText().toString(),op2_et.getText().toString(),op3_et.getText().toString(),op4_et.getText().toString(),qno);
                    call.enqueue(new Callback<Quiz>() {
                        @Override
                        public void onResponse(Call<Quiz> call, Response<Quiz> response) {
                            if(response.body().getResponse().equals("ok")){
                                alert(QuizMaste.this,"News","Done",R.color.green);
//                                Toast.makeText(QuizMaste.this, "sucess", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }else{

                                alert(QuizMaste.this,"Quiz",response.errorBody().toString(),R.color.red);
//                                alert(QuizMaste.this,"News","Please try again after some time",R.color.red);
                            }
                        }

                        @Override
                        public void onFailure(Call<Quiz> call, Throwable t) {
                            apifailure(t,QuizMaste.this);
                        }
                    });
                }
            }
        });
    }
}