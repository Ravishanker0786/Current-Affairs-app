package com.prinfosys.news48;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.prinfosys.news48.Admin.AdminLogin;
import com.prinfosys.news48.Admin.Insight.InsightAdminView;
import com.prinfosys.news48.Admin.MyClient;
import com.prinfosys.news48.Admin.Quiz.QuizMain;
import com.prinfosys.news48.Admin.topic.topic;
import com.prinfosys.news48.SqlLite.DBHelper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.prinfosys.news48.MainActivity.alert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

public class Left extends _SwipeActivityClass implements GoogleApiClient.OnConnectionFailedListener  {
    Button quiz,poll,insight;
    RelativeLayout btn2;
    GridView gridView;
    ArrayList<topic> listDataList;
    TopicGridView topicGridView;
    TextView textView,notification_txt,userName,userEmail,userId;
    LinearLayout allnews,book,rec;
    ImageView cal,search,ret,notification_img,setting_modal,close_btn,profileImage;
    EditText searchtxt;
    Switch widget_switch;
    DBHelper mydb;
    Menu menu_1;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    GoogleSignInClient mGoogleSignInClient;
    SignInButton btn;
    Button signOutButton;
    LinearLayout searchLayout;
    public DrawerLayout drawerLayout;
    boolean mSlideState;
    View hView;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    private static final int RC_SIGN_IN = 007;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left);
        quiz = findViewById(R.id.quiz_menu);
        insight = findViewById(R.id.insights_menu);
//        gridView = findViewById(R.id.grid_view);
        textView = findViewById(R.id.st);
        allnews = findViewById(R.id.all);
        cal = findViewById(R.id.calender);
        search = findViewById(R.id.search);
//        widget_switch = findViewById(R.id.widget_switch);
        searchtxt = findViewById(R.id.stext);
        navigationView = findViewById(R.id.navigation_view);
        hView =  navigationView.getHeaderView(0);
        searchLayout = findViewById(R.id.search_layout);
        userEmail = hView.findViewById(R.id.profile_email);
        userName = hView.findViewById(R.id.profile_name);
        profileImage = hView.findViewById(R.id.profile_image);
        //        ret = findViewById(R.id.retur);
        //        btn2 = findViewById(R.id.bc);
        book = findViewById(R.id.myfeedl);
        rec = findViewById(R.id.trndgl);
        mSlideState=true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            this.invalidateOptionsMenu();
        }

//        widget_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                Toast.makeText(Left.this, "testing", Toast.LENGTH_SHORT).show();
//            }
//        });

        gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //        drawerLayout.openDrawer(Gravity.LEFT);

//        notification_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (isChecked) {
//                    // The toggle is enabled
//                    FirebaseMessaging.getInstance().subscribeToTopic("test").addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            String msg = "done";
//                            if (!task.isSuccessful()) {
//                                msg = "failed";
//                            }
//                            Log.d("tag_msg", msg);
//                        }
//                    });
////                    Toast.makeText(Left.this, "checked", Toast.LENGTH_SHORT).show();
//                    // mark first time has ran.
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putBoolean("subscribed_user", true);
//                    editor.commit();
//                } else {
//                    // The toggle is disabled
//                    FirebaseMessaging.getInstance().unsubscribeFromTopic("test").addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            String msg = "unsubscribe done";
//                            if (!task.isSuccessful()) {
//                                msg = "unsubscribe failed";
//                            }
//                            Log.d("tag_msg", msg);
//                        }
//                    });
////                    Toast.makeText(Left.this, "unchecked", Toast.LENGTH_SHORT).show();
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putBoolean("subscribed_user", false);
//                    editor.commit();
////                    notification_switch.setChecked(false);
//                }
//            }
//        });
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor=sharedPreferences.edit();

        //get the menu from the navigation view
        Menu menu=navigationView.getMenu();

        //get switch view
        SwitchCompat switchcompat=(SwitchCompat) MenuItemCompat.getActionView(menu.findItem(R.id.notifications)).findViewById(R.id.drawer_switch);

        if (sharedPreferences.getBoolean("subscribed_user", false)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("subscribed_user", true);
            editor.commit();
            switchcompat.setChecked(true);
        }else{
//            Toast.makeText(Left.this, String.valueOf(sharedPreferences.getBoolean("subscribed_user",true)), Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("subscribed_user", false);
            editor.commit();
            switchcompat.setChecked(false);
        }

        //add listener
        switchcompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //your action
                if (isChecked) {
                    // The toggle is enabled
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
                    Toast.makeText(Left.this, "checked", Toast.LENGTH_SHORT).show();
                    // mark first time has ran.
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("subscribed_user", true);
                    editor.commit();
                } else {
                    // The toggle is disabled
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("test").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String msg = "unsubscribe done";
                            if (!task.isSuccessful()) {
                                msg = "unsubscribe failed";
                            }
                            Log.d("tag_msg", msg);
                        }
                    });
                    Toast.makeText(Left.this, "unchecked", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("subscribed_user", false);
                    editor.commit();
//                    notification_switch.setChecked(false);
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.about_us:
                        if (isNetworkAvailable()){
                            Intent intent = new Intent(Left.this,AboutPrivacyContact.class);
                            intent.putExtra("view_url","https://www.topprsiq.com/about-us/");
                            intent.putExtra("heading","About Us");
                            startActivity(intent);
                        }else{
                            alert(Left.this,"Error","No Internet connection",R.color.red);
                        }
                        return true;
                    case R.id.contact_us:
                        if (isNetworkAvailable()){
                            Intent intent = new Intent(Left.this,AboutPrivacyContact.class);
                            intent.putExtra("view_url","https://www.topprsiq.com/contact-us/");
                            intent.putExtra("heading","Contact Us");
                            startActivity(intent);
                        }else{
                            alert(Left.this,"Error","No Internet connection",R.color.red);
                        }
                        return true;
                    case R.id.privacy:
                        if (isNetworkAvailable()){
                            Intent intent = new Intent(Left.this,AboutPrivacyContact.class);
                            intent.putExtra("view_url","https://www.topprsiq.com/privacy-policy/");
                            intent.putExtra("heading","Privacy Policy");
                            startActivity(intent);
                        }else{
                            alert(Left.this,"Error","No Internet connection",R.color.red);
                        }
                        return true;
                    case R.id.notifications:
                        //showHelp();
//                        Menu nav_Menu = navigationView.getMenu();
//                        nav_Menu.findItem(R.id.SignInBtn).setVisible(true);
//                        Toast.makeText(Left.this, "est", Toast.LENGTH_SHORT).show();
                        item.getActionView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(Left.this, "testing", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return true;
                    case R.id.SignInBtn:
                        if(item.getTitle() == "Google Sign In"){
                            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestEmail()
                                    .build();
                            mGoogleSignInClient = GoogleSignIn.getClient(Left.this, gso);
                            signIn();
                        }else{
                            g_logout();
                        }

                        return true;
                    case R.id.nav_logout:
//                        Toast.makeText(Left.this, "before", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return true;
                }

            }
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor=sharedPreferences.edit();

        rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()){
                    Intent intent = new Intent(Left.this,MainActivity.class);
                    intent.putExtra("recent","1");
                    startActivity(intent);
                }else{
                    alert(Left.this,"Error","No Internet connection",R.color.red);
                }

            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()){
                    mydb = new DBHelper(Left.this);
                    ArrayList array_list = mydb.getAllCotacts();
                    String tempString = "";
                    for(int x = 0; x < array_list.size(); x++) {
                        tempString = tempString + ", " + array_list.get(x);
                    }
                    if (tempString.length()<=0){
                        Toast.makeText(Left.this, "No bookmark yet", Toast.LENGTH_SHORT).show();
                    }else{
                        tempString = tempString.substring(2);
                        Intent intent = new Intent(Left.this,MainActivity.class);
                        intent.putExtra("uno",tempString);
                        startActivity(intent);
                    }
                }else{
                    alert(Left.this,"Error","No Internet connection",R.color.red);
                }
            }
        });



        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()){
                    String text = searchtxt.getText().toString();
                    if (text.length()<=0){
                        Toast.makeText(Left.this, "Search field is empty", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent(Left.this,MainActivity.class);
                        intent.putExtra("search",text);
                        startActivity(intent);
                    }
                }else {
                    alert(Left.this,"Error","No Internet connection",R.color.red);
                }
            }
        });



        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()){
                    int mYear, mMonth, mDay;
                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(Left.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

//                                        monthOfYear= 11;

                                    String sMonth = "";
                                    if (monthOfYear < 10) {
                                        sMonth = "0" + String.valueOf(monthOfYear + 1);
                                    } else {
                                        sMonth = String.valueOf(monthOfYear);
                                    }

                                    String sDay = "";
                                    if (dayOfMonth < 10) {
                                        sDay = "0" + String.valueOf(dayOfMonth + 1);
                                    } else {
                                        sDay = String.valueOf(dayOfMonth);
                                    }


                                    String d = year + "-" + sMonth + "-" + sDay;
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

                                    String date = new SimpleDateFormat("yyyy-mm-dd").format(new Date());


//                                SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                    try {
                                        Date d1 = dateFormat.parse(date);
                                        Date d2 = dateFormat.parse(d);

                                        if(d1.compareTo(d2) > 0) {
                                            Intent intent = new Intent(Left.this,MainActivity.class);
                                            intent.putExtra("date",d);
                                            startActivity(intent);
                                            Toast.makeText(Left.this,"Date 1 occurs after Date 2", Toast.LENGTH_SHORT).show();
                                        } else if(d1.compareTo(d2) < 0) {
                                            Toast.makeText(Left.this,"Date 1 occurs before Date 2", Toast.LENGTH_SHORT).show();
                                        } else if(d1.compareTo(d2) == 0) {
                                            Toast.makeText(Left.this,"Both dates are equal", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }else {
                    alert(Left.this,"Error","No Internet connection",R.color.red);
                }
            }
        });

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()){
                    Intent intent = new Intent(Left.this, QuizMain.class);
                    intent.putExtra("type","user");
                    startActivity(intent);
                }else {
                    alert(Left.this,"Error","No Internet connection",R.color.red);
                }
            }
        });


        insight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Left.this, InsightAdminView.class);
                startActivity(intent);
            }
        });

        if (isNetworkAvailable()){
            Call<topic> call = MyClient.getInstance().getMyApi().gettopic("all");
            call.enqueue(new Callback<topic>() {
                @Override
                public void onResponse(Call<topic> call, Response<topic> response) {
                    if (response.body().getResponse().contains("ok")){
//                        listDataList = response.body().getTopicList();
//                        topicGridView = new TopicGridView(Left.this,R.layout.gridview,listDataList);
//                        gridView.setAdapter(topicGridView);
                    }
                }

                @Override
                public void onFailure(Call<topic> call, Throwable t) {

                }
            });
        }else {
            alert(Left.this,"Error","No Internet connection",R.color.red);
        }


        allnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()){
                    Intent intent = new Intent(Left.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    alert(Left.this,"Error","No Internet connection",R.color.red);
                }
            }
        });


        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (isNetworkAvailable()){
                    Intent intent = new Intent(Left.this, AdminLogin.class);
                    startActivity(intent);
                }else{
                    alert(Left.this,"Error","No Internet connection",R.color.red);
                }
                return false;
            }
        });


    }

    @Override
    protected void onSwipeRight() {

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.back_arrow_left:
                onBackPressed();
                return true;
            case R.id.search_left:
//                Toast.makeText(Left.this, "yo", Toast.LENGTH_SHORT).show();
                MenuItem item_1 = menu_1.findItem(R.id.search_close_1);
                item_1.setVisible(true);
                item.setVisible(false);
                searchLayout.setVisibility(View.VISIBLE);
                return true;
            case R.id.search_close_1:
                MenuItem item_2 = menu_1.findItem(R.id.search_left);
                item_2.setVisible(true);
                item.setVisible(false);
                searchLayout.setVisibility(View.GONE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void g_logout(){

        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.SignInBtn).setTitle("Google Sign In");
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();
        GoogleSignInClient googleSignInClient=GoogleSignIn.getClient(this,gso);
        googleSignInClient.signOut();

        userName.setText("Guest");
        userEmail.setText("Not Signed In");
        try{
            Glide.with(this).load(getImage("profile_img")).into(profileImage);
        }catch (NullPointerException e){
            //            Toast.makeText(getApplicationContext(),"image not found",Toast.LENGTH_LONG).show();
        }
//        navigationView = (NavigationView) findViewById(R.id.nav);


    }


    public int getImage(String imageName) {
        int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());
        return drawableResourceId;
    }

    @Override
    protected void onSwipeLeft() {
        //        onBackPressed();
    }

    private void signIn() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,RC_SIGN_IN);
    }

    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.stopAutoManage(Left.this);
        googleApiClient.disconnect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr= Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.SignInBtn).setTitle("Logout");
            GoogleSignInResult result=opr.get();
            handleSignInResult(result);
        }else{
            Menu nav_Menu = navigationView.getMenu();

            nav_Menu.findItem(R.id.SignInBtn).setTitle("Google Sign In");
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }
    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            Menu nav_Menu = navigationView.getMenu();
//            Toast.makeText(Left.this, "hide", Toast.LENGTH_SHORT).show();
            nav_Menu.findItem(R.id.SignInBtn).setTitle("Logout");
            GoogleSignInAccount account=result.getSignInAccount();
            userName.setText(account.getDisplayName());
            userEmail.setText(account.getEmail());
//            userId.setText(account.getId());
            try{
                if(String.valueOf(account.getPhotoUrl()) != "null"){
                    Glide.with(this).load(account.getPhotoUrl()).into(profileImage);
                }else{
                    Glide.with(this).load(getImage("profile_img")).into(profileImage);
                }
//                Toast.makeText(Left.this,String.valueOf(account.getPhotoUrl()) , Toast.LENGTH_SHORT).show();
            }catch (NullPointerException e){
//                Toast.makeText(getApplicationContext(),"image not found",Toast.LENGTH_LONG).show();
                Glide.with(this).load(getImage("profile_img")).into(profileImage);
            }

        }else{
//            gotoMainActivity();
//            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.SignInBtn).setTitle("Google Sign In");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
//    private void handleSignInResult(GoogleSignInResult result){
//        if(result.isSuccess()){
//
//        }else{
//            Toast.makeText(getApplicationContext(),"Sign in cancel",Toast.LENGTH_LONG).show();
//        }
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //this.optionsMenu = menu;
        menu_1 = menu;
        //MenuInflater inflater = getMenuInflater(); //ERROR<-----------
        getMenuInflater().inflate(R.menu.menu_item_left, menu);
        //return super.onCreateOptionsMenu(menu); // in Fragment cannot be applied <------------
        return true;
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}