package com.prinfosys.news48;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.prinfosys.news48.Admin.MyClient;
import com.prinfosys.news48.Admin.topic.topic;
import com.prinfosys.news48.Model.Insight;
import com.prinfosys.news48.Model.Login;
import com.prinfosys.news48.Utils.Utils;
import com.tapadoo.alerter.Alerter;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

import static com.prinfosys.news48.Api.BASE_URL;
import static com.prinfosys.news48.LaravelApi.BASE_URL_1;

import java.util.ArrayList;

public class MainActivity extends  AppCompatActivity {

    public static ArrayList<Insight> insightList = new ArrayList<>();
    public static String jsonInsight;
    public static ArrayList<topic> listDataList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String newsUrl;
    Runnable runnable;
    Runnable Insight;
    Handler handler;
    String jsonString,date,search;
    ImageView progressBar;
    String weburl,tid,tname,nuno,recent;
    LinearLayout linearLayout;
    Button btn,btn1,btn2;
    TextView loading,tv_weburl,head;
    Toolbar myToolbar;
    RelativeLayout main_view;
    public static Boolean status;

    VerticalViewPager verticalViewPager;

    private boolean isTouch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        linearLayout = findViewById(R.id.top);
        btn = findViewById(R.id.right);
        btn1 = findViewById(R.id.back_act1);
        btn2 = findViewById(R.id.bc);
        head = findViewById(R.id.txt1);
        main_view = findViewById(R.id.activity_feed_polls);
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        verticalViewPager = findViewById(R.id.vPager);
        myToolbar.setTitle("All News");
        myToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.reorder));
        setSupportActionBar(myToolbar);
        hide_toolbar();
//        myToolbar.setVisibility(View.GONE);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
//                Intent intent = new Intent(MainActivity.this,Left.class);
//                startActivity(intent);
                Utils.goToFragment(MainActivity.this, new MenuFragment(), R.id.activity_feed_polls, true, true);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Privacy Policiy", Toast.LENGTH_SHORT).show();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reload();
            }
        });

//        view1.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
//                    Toast.makeText(MainActivity.this, "testing", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//                return false;
//            }
//
//        });
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this,Left.class);
//                startActivity(intent);
//
//            }
//        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Left.class);
                startActivity(intent);

            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor=sharedPreferences.edit();

        if (!sharedPreferences.getBoolean("subscribed", false)) {
            // <---- run your one time code here
            FirebaseMessaging.getInstance().subscribeToTopic("test").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    String msg = "done";
                    if (!task.isSuccessful()) {
                        msg = "failed";
                    }
                    Log.d("tag_msg", msg);
                }
            });
            // mark first time has ran.
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("subscribed", true);
            editor.putBoolean("subscribed_user", true);
            editor.commit();
        }

        tv_weburl = findViewById(R.id.weburllll);
        progressBar=findViewById(R.id.progress_bar);
        loading=(TextView)findViewById(R.id.loading);
        handler=new Handler();
        progressBar.setVisibility(View.VISIBLE);
        tid = getIntent().getStringExtra("tid");
        date = getIntent().getStringExtra("date");
        search = getIntent().getStringExtra("search");
        nuno = getIntent().getStringExtra("uno");
        recent = getIntent().getStringExtra("recent");
        try {
            newsUrl=getIntent().getExtras().getString("newsUrl",null);
            Log.e("tag",newsUrl);
            if (newsUrl==null) {
                if (recent != null){
                    newsUrl = BASE_URL+"shownews.php?type=all&recent=1";
                    head.setText("Recents");
                } else {
                    if (nuno != null){
                        newsUrl = BASE_URL+"shownews.php?type=all&uno="+nuno;
                        head.setText("Bookmarks");
                    } else {
                        if (search != null){
                            newsUrl = BASE_URL+"shownews.php?type=all&search="+search;
                        } else {
                            if (date != null){
                                newsUrl = BASE_URL+"shownews.php?type=all&date="+date;
                            } else {
                                if (tid != null){
                                    tname = getIntent().getStringExtra("tname");
                                    head.setText(tname);
                                    newsUrl = BASE_URL+"shownews.php?type=cat&cat="+tid;
                                } else {
                                    head.setText("All News");
                                    newsUrl = BASE_URL+"shownews.php?type=all";
                                }
                            }
                        }
                    }
                }
            } else {
                if (recent != null){
                    newsUrl = BASE_URL+"shownews.php?type=all&recent=1";
                    head.setText("Recents");
                } else {
                    if (nuno != null){
                        newsUrl = BASE_URL+"shownews.php?type=all&uno="+nuno;
                        head.setText("Bookmarks");
                    } else {
                        if (search != null){
                            newsUrl = BASE_URL+"shownews.php?type=all&search="+search;
                        } else {
                            if (date != null){
                                newsUrl = BASE_URL+"shownews.php?type=all&date="+date;
                            } else {
                                if (tid != null){
                                    tname = getIntent().getStringExtra("tname");
                                    head.setText(tname);
                                    newsUrl = BASE_URL+"shownews.php?type=cat&cat="+tid;
                                } else {
                                    head.setText("All News");
                                    newsUrl = BASE_URL+"shownews.php?type=all";
                                }
                            }
                        }
                    }
                }

            }
        }
        catch (Exception E)
        {

            if (recent != null){
                newsUrl = BASE_URL+"shownews.php?type=all&recent=1";
                head.setText("Recents");
            }else {
                if (nuno != null){
                    newsUrl = BASE_URL+"shownews.php?type=all&uno="+nuno;
                    head.setText("Bookmarks");
                }else {
                    if (search != null){
                        newsUrl = BASE_URL+"shownews.php?type=all&search="+search;
                    }else{
                        if (date != null){
                            newsUrl = BASE_URL+"shownews.php?type=all&date="+date;
                        }else{
                            if (tid != null){
                                tname = getIntent().getStringExtra("tname");
                                head.setText(tname);
                                newsUrl = BASE_URL+"shownews.php?type=cat&cat="+tid;
                            }else{
                                head.setText("All News");
                                newsUrl = BASE_URL+"shownews.php?type=all";
                            }
                        }
                    }
                }
            }
        }



        runnable=new Runnable() {
            @Override
            public void run() {
//                initSwipePager();
                topic_data();

                Log.i("calling_fun","test_12");
                JSONAsyncTask getData=new JSONAsyncTask();
                getData.execute();
            }
        };

        try {
            handler.postDelayed(runnable, 10);
        }
        catch (Exception E)
        {
            topic_data();
//            initSwipePager();
            JSONAsyncTask getData=new JSONAsyncTask();
            getData.execute();
        }

        Insight = new Runnable() {
            @Override
            public void run() {
                Log.i("calling_fun","test_12");
                data();
                JSONInsightTask getInsight = new JSONInsightTask();
                getInsight.execute();
            }
        };

        try {
            handler.postDelayed(Insight, 10);
        }
        catch (Exception E)
        {
//            initSwipePager();
//            topic_data();
            data();

            JSONInsightTask getInsight = new JSONInsightTask();
            getInsight.execute();
        }


        Window window = MainActivity.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
//        window.setStatusBarColor(Color.parseColor("#ddd4cb"));

//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);




//        scheduleNotification();
//    scheduleRepeatingElapsedNotification(getApplicationContext());



    }
//    public int getLastCount() {
//        return this.weburl;
//    }

    public void setWeburlll(String weburl) {
        tv_weburl.setText(weburl);
    }


    protected void onSwipeRight() {
//        Intent intent = new Intent(MainActivity.this,Right.class);
//        intent.putExtra("weburl",VerticlePagerAdapter.webu);
//        startActivity(intent);
//        VerticlePagerAdapter.testing(view);
//        Toast.makeText(this, VerticlePagerAdapter.webu, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //this.optionsMenu = menu;
        //MenuInflater inflater = getMenuInflater(); //ERROR<-----------
        getMenuInflater().inflate(R.menu.menu_refresh, menu);
        //return super.onCreateOptionsMenu(menu); // in Fragment cannot be applied <------------
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.refresh:
                reload();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void show_toolbar(){
        if (myToolbar.getVisibility() == View.GONE) {
            myToolbar.setVisibility(View.VISIBLE);
            myToolbar.setAlpha(0.0f);
            // Start the animation
            myToolbar.animate()
                    .translationY(10)
                    .alpha(1.0f)
                    .setDuration(500)
                    .setListener(null);

            //        Toast.makeText(MainActivity.this, "entered", Toast.LENGTH_SHORT).show();
            isTouch = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //                Toast.makeText(MainActivity.this, "5 sec", Toast.LENGTH_SHORT).show();
                    myToolbar.animate()
                            .setDuration(1000)
                            .translationY(0)
                            .alpha(0.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    myToolbar.setVisibility(View.GONE);

                                }
                            });
                }
            }, 5000);
        }
//        isTouch = true;
    }

    public void hide_toolbar(){
//            Toast.makeText(MainActivity.this, "entered", Toast.LENGTH_SHORT).show();
        isTouch = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myToolbar.animate()
                        .setDuration(1000)
                        .translationY(0)
                        .alpha(0.0f)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                myToolbar.setVisibility(View.GONE);

                            }
                        });
                isTouch = false;
            }
        }, 5000);

//        isTouch = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int X = (int) event.getX();
        int Y = (int) event.getY();
        int eventaction = event.getAction();

        switch (eventaction) {
            case MotionEvent.ACTION_DOWN:
                myToolbar.setVisibility(View.VISIBLE);
                hide_toolbar();
                break;
        }
        return true;
    }

    protected void onSwipeLeft() {
        Intent intent = new Intent(MainActivity.this,Left.class);
        startActivity(intent);
//
//        Toast.makeText(this, "Left", Toast.LENGTH_SHORT).show();
    }

//    private void scheduleNotification() {
//        NotificationHelper.enableBootReceiver(getApplicationContext());
//        NotificationHelper.scheduleRepeatingElapsedNotification(getApplicationContext());
//    }

    class JSONInsightTask extends AsyncTask<String,Void,Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {

            OkHttpClient client = new OkHttpClient();
            Request request2 = new Request.Builder()
                    .url(BASE_URL_1+"insight/get")
                    .build();

            try {
//                Toast.makeText(MainActivity.this, newsUrl, Toast.LENGTH_LONG).show();
                Response insight;
                insight = client.newCall(request2).execute();
                jsonInsight=insight.body().string();
//                Log.v("789456123",jsonInsight);
//                editor.putString("cachedInsight", jsonInsight);
//                editor.apply();
                JSONObject jsonObject = new JSONObject(jsonInsight);
            }
            catch (Exception E)
            {
                Log.v("insight_error_cached",E.toString());
//                    Toast.makeText(getApplicationContext(), "hllo", Toast.LENGTH_LONG).show();
                jsonInsight=sharedPreferences.getString("cachedInsight", null);
            }

            return true;
        }
        protected void onPostExecute(Boolean result) {

            try {
//                progressBar.setVisibility(View.GONE);
//                loading.setVisibility(View.GONE);
//                linearLayout.setVisibility(View.VISIBLE);

//                Log.v("insight",jsonInsight);

//                Toast.makeText(MainActivity.this, jsonInsight, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Log.v("insight_error",e.toString());
            }
        }
    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... strings) {
//            Toast.makeText(MainActivity.this, "hllo", Toast.LENGTH_SHORT).show();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(newsUrl)
                    .build();

            try {
//                Toast.makeText(MainActivity.this, newsUrl, Toast.LENGTH_LONG).show();
                Response response;
                response = client.newCall(request).execute();
                jsonString=response.body().string();
                Log.e("t",response.toString());
                editor.putString("cachedData", jsonString);
                editor.apply();
                JSONObject jsonObject = new JSONObject(jsonString);
            }
            catch (Exception E)
            {
//                    Toast.makeText(getApplicationContext(), "hllo", Toast.LENGTH_LONG).show();
                jsonString=sharedPreferences.getString("cachedData", null);
            }

            return true;
        }

        protected void onPostExecute(Boolean result) {
//            VerticalViewPager verticalViewPager = (VerticalViewPager) findViewById(R.id.vPager);
            try {
                progressBar.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
//                Log.v("insight",jsonInsight);
                verticalViewPager.setAdapter(new VerticlePagerAdapter(MainActivity.this , jsonString,verticalViewPager));
            } catch (Exception e) {
//            e.printStackTrace();
            }
        }
    }
    public static void alert(Context context , String title , String body , int color){
        Alerter.create((Activity) context)
                .setTitle(title)
                .setText(body)
                .setIcon(R.drawable.checked)
                .setBackgroundColorRes(color)
                .setDuration(1500)
                .show();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void apifailure(Throwable t , Context context){
        if (t.toString().contains("Unable to resolve host")){
            alert(context,"Connection Failed","Check your Internet Connection",R.color.red);
        }else{
            alert(context,"Error","Please try again after some time.......",R.color.red);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        reload();
    }

    public void reload(){
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);//Start the same Activity
        finish(); //finish Activity.
    }


    public void data(){
        Call<com.prinfosys.news48.Model.Insight> call = MyClientLaravel.getInstance().getLaravelApi().getinsight();
        call.enqueue(new Callback<Insight>() {
            @Override
            public void onResponse(Call<Insight> call, retrofit2.Response<Insight> response) {
                if (response.isSuccessful()){
                    if(response.body().getResponse().equals("ok")) {

                        insightList = response.body().getBody();
                    }
                }else{
                    Toast.makeText(MainActivity.this,response.errorBody().toString() , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Insight> call, Throwable t) {

            }
        });
    }
    public void topic_data(){
//        Call<com.prinfosys.news48.Model.Insight> call = MyClientLaravel.getInstance().getLaravelApi().getinsight();
//        call.enqueue(new Callback<Insight>() {
//            @Override
//            public void onResponse(Call<Insight> call, retrofit2.Response<Insight> response) {
//                if (response.isSuccessful()){
//                    if(response.body().getResponse().equals("ok")) {
//                        insightList = response.body().getBody();
//                        Toast.makeText(MainActivity.this, "completed", Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                    Toast.makeText(MainActivity.this,response.errorBody().toString() , Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Insight> call, Throwable t) {
//
//            }
//        });
        Call<topic> call = MyClient.getInstance().getMyApi().gettopic("all");
        call.enqueue(new Callback<topic>() {
            @Override
            public void onResponse(Call<topic> call, retrofit2.Response<topic> response) {
//                Toast.makeText(getContext(), "calling_finished", Toast.LENGTH_SHORT).show();
                if (response.body().getResponse().contains("ok")){
                    listDataList = response.body().getTopicList();
//                    Toast.makeText(MainActivity.this, "completed", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<topic> call, Throwable t) {

            }
        });
    }


    public void go_to_left(Context context){
//        Toast.makeText(MainActivity.this, "tes", Toast.LENGTH_SHORT).show();
        Utils.goToFragment(context, new MenuFragment(), R.id.activity_feed_polls, true, true);
    }

    public static void socialLogin(String email,String name,Context mContext){
        Call<Login> call = MyClientLaravel.getInstance().getLaravelApi().socialLogin(email,name);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, retrofit2.Response<Login> response) {
                if (response.isSuccessful()){
                    if(response.body().getStatus()) {

                        status = response.body().getStatus();


                    }
                    alert(mContext,"Success",response.body().getMessage(),R.color.green);
                }else{
                    alert(mContext,"Error",response.errorBody().toString(),R.color.red);
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });
    }



}
