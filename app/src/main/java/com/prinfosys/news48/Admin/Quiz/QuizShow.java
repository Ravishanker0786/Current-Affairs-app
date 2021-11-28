package com.prinfosys.news48.Admin.Quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.prinfosys.news48.Adapters.QuizQuesListAdapter;
import com.prinfosys.news48.Admin.AddNews;
import com.prinfosys.news48.Admin.MyClient;
import com.prinfosys.news48.Admin.NewsBody;
import com.prinfosys.news48.Admin.ShowNew;
import com.prinfosys.news48.Admin.ShowNewsAdapter;
import com.prinfosys.news48.Model.Quiz;
import com.prinfosys.news48.MyClientLaravel;
import com.prinfosys.news48.R;
import com.prinfosys.news48.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizShow extends AppCompatActivity {
    SwipeMenuListView listView;
    private static final String TAG = "MainActivity";
    ArrayList<Quiz> quesList;
    QuizQuesListAdapter quizQuesListAdapter;
    String qno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_show);
        listView = (SwipeMenuListView) findViewById(R.id.listView);
        qno = getIntent().getStringExtra("qno");
        Toast.makeText(QuizShow.this, qno, Toast.LENGTH_SHORT).show();

    showquiz();


//        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//                switch (index) {
//                    case 0:
//                        Log.d(TAG, "onMenuItemClick: clicked item " + index);
//                        break;
//                    case 1:
//                        Log.d(TAG, "onMenuItemClick: clicked item " + index);
//                        break;
//                }
//                // false : close the menu; true : not close the menu
//                return false;
//            }
//        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        MenuItem add = menu.findItem(R.id.add);
        add.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(QuizShow.this, QuizMaste.class);
                intent.putExtra("qno",qno);
                intent.putExtra("type","insert");
                startActivity(intent);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void showquiz(){
        Call<Quiz> call = RetrofitClient.getInstance().getMyApi().getQuesListByQno("all",qno);
        call.enqueue(new Callback<Quiz>() {
            @Override
            public void onResponse(Call<Quiz> call, Response<Quiz> response) {
//                Toast.makeText(QuizShow.this, response.body().getResponse(), Toast.LENGTH_SHORT).show();
                if (response.body().getResponse().equals("ok")){
                    quesList = response.body().getBody();
                    quizQuesListAdapter = new QuizQuesListAdapter(QuizShow.this,quesList);
                    listView.setAdapter(quizQuesListAdapter);
                    SwipeMenuCreator creator = new SwipeMenuCreator() {

                        @Override
                        public void create(SwipeMenu menu) {
                            // create "open" item
                            SwipeMenuItem openItem = new SwipeMenuItem(
                                    getApplicationContext());
                            // set item background
                            openItem.setBackground(new ColorDrawable(Color.rgb(0x00, 0x66,
                                    0xff)));
                            // set item width
                            openItem.setWidth(170);
                            // set item title
                            openItem.setTitle("Open");
                            // set item title fontsize
                            openItem.setTitleSize(18);
                            // set item title font color
                            openItem.setTitleColor(Color.WHITE);
                            // add to menu
                            menu.addMenuItem(openItem);

                            // create "delete" item
                            SwipeMenuItem deleteItem = new SwipeMenuItem(
                                    getApplicationContext());
                            // set item background
                            deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                                    0x3F, 0x25)));
                            // set item width
                            deleteItem.setWidth(170);
                            // set a icon
                            deleteItem.setIcon(R.drawable.ic_del);
                            // add to menu
                            menu.addMenuItem(deleteItem);
                        }
                    };

                    listView.setMenuCreator(creator);
                    listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                            switch (index) {
                                case 0:
//                                    String uques = quesList.get(position).getQues();
                                    Intent intent = new Intent(QuizShow.this,QuizMaste.class);
                                    intent.putExtra("qno",qno);
                                    intent.putExtra("qid",quesList.get(position).getQid());
                                    intent.putExtra("ques",quesList.get(position).getQues());
                                    intent.putExtra("op1",quesList.get(position).getOption1());
                                    intent.putExtra("op2",quesList.get(position).getOption2());
                                    intent.putExtra("op3",quesList.get(position).getOption3());
                                    intent.putExtra("op4",quesList.get(position).getOption4());
                                    intent.putExtra("ans",quesList.get(position).getAns());
                                    intent.putExtra("type","update");
                                    startActivity(intent);
//                                    Toast.makeText(QuizShow.this, uques, Toast.LENGTH_SHORT).show();
                                    break;

                                case 1:
                                    String dqid = quesList.get(position).getQid();
                                    Call<Quiz> call = RetrofitClient.getInstance().getMyApi().quiz("delete",dqid,"0"
                                            ,"0","0","0","0","0","0");
                                    call.enqueue(new Callback<Quiz>() {
                                        @Override
                                        public void onResponse(Call<Quiz> call, Response<Quiz> response) {
                                            showquiz();
                                        }

                                        @Override
                                        public void onFailure(Call<Quiz> call, Throwable t) {

                                        }
                                    });
                                    break;
                            }
                            return false;
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Quiz> call, Throwable t) {

            }
        });
    }



    @Override
    protected void onResume() {
        showquiz();
        super.onResume();
    }
}