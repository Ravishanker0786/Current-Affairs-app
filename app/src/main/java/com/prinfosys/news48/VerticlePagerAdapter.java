package com.prinfosys.news48;

import static com.prinfosys.news48.MainActivity.alert;
import static com.prinfosys.news48.MainActivity.apifailure;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.matthewtamlin.sliding_intro_screen_library.indicators.DotIndicator;
import com.prinfosys.news48.Admin.AddNews;
import com.prinfosys.news48.Admin.Insight.InsightAdapter;
import com.prinfosys.news48.Admin.Insight.InsightAdminView;
import com.prinfosys.news48.Admin.MyClient;
import com.prinfosys.news48.Model.Insight;
import com.prinfosys.news48.Model.pass;
import com.prinfosys.news48.SqlLite.DBHelper;
import com.prinfosys.news48.SqlLite.DBHelperLike;
import com.prinfosys.news48.Utils.Utils;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.prinfosys.news48.LaravelApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerticlePagerAdapter extends PagerAdapter {

    JSONObject jsonObject=null;
    JSONObject jsonInsightObject=null;
    public static String webu;
    int oldposisition = 0;
    int newposition,insight_index = 0;
    float x1,x2;
    private DBHelper mydb ;
    TextToSpeech t1;
    ViewPagerAdapter mViewPagerAdapter;
    private DBHelperLike mydbLike ;

    LinearLayout main_card_view,slider_layout;
    ArrayList<String> insight_urls;
    DotIndicator indicator;

    String mResources[] = {"NYSE Parent to Buy Chicago Stock Exchange",
            "JP Morgan's Jamie Dimon: \"not unreasonable\" for US to push for fair trade with China"};

    String mDescription[]={"The owner of the New York Stock Exchange has reached a deal to buy the Chicago Stock Exchange, after a two-year acquisition effort from a Chinese-led investor group failed.",
            "JPMorgan Chase CEO Jamie Dimon says it is \"not unreasonable\" for Trump to fight for better trade terms with China. In letter to shareholders, banker says market could be caught off guard by quicker pace of rate hikes."};

    Context mContext;
    LayoutInflater mLayoutInflater;

    VerticalViewPager verticalViewPager;

    int adsCounter=0,insight_counter=1;
    float initialX, initialY;
    private InterstitialAd mInterstitialAd;


    public VerticlePagerAdapter(Context context, String response,VerticalViewPager verticalViewPager) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.verticalViewPager = verticalViewPager;
        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId(mContext.getResources().getString(R.string.admob_interstitial));
        try {
            jsonObject= new JSONObject(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //        try {
        //
        //            Toast.makeText(mContext,String.valueOf(jsonInsightObject.getJSONArray("body").length()) , Toast.LENGTH_SHORT).show();
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }
    }
    @Override
    public int getCount() {
        try {
            return jsonObject.getJSONArray("body").length();
        } catch (Exception e) {
            return 1;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }
    @Override
    @SuppressLint("ClickableViewAccessibility")
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.fragment_main, container, false);
        t1=new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        slider_layout = itemView.findViewById(R.id.slider_insights);
        main_card_view = itemView.findViewById(R.id.main_card_view);
        CardView cardView=(CardView)itemView.findViewById(R.id.card_view);
        TextView label = (TextView) itemView.findViewById(R.id.textView);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

        TextView newsDesc=(TextView) itemView.findViewById(R.id.textView2);
        ImageView speek = (ImageView) itemView.findViewById(R.id.speek);
        ImageView stop = (ImageView) itemView.findViewById(R.id.stop);
        TextView source=(TextView)itemView.findViewById(R.id.textView_link);
        TextView web = (TextView)itemView.findViewById(R.id.weburlll);
        ImageView star = (ImageView) itemView.findViewById(R.id.star);
        ImageView sstar = (ImageView) itemView.findViewById(R.id.sstar);
        ImageView like = (ImageView) itemView.findViewById(R.id.like);
        ImageView llike = (ImageView) itemView.findViewById(R.id.llike);
        SliderView  insight_slider = itemView.findViewById(R.id.imageSlider);
        ImageView close_btn = itemView.findViewById(R.id.close_btn);

//        DotIndicator indicator = itemView.findViewById(R.id.dots_insight);
        AdView mAdView;
        AdView adView = new AdView(mContext);
        adView.setAdSize(AdSize.BANNER);

        MobileAds.initialize(mContext, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = itemView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        adsCounter++;
        if (adsCounter%5==0)
        {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }
        if (adsCounter%10==0)
        {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                verticalViewPager.setCurrentItem(verticalViewPager.getCurrentItem()+1);

            }
        });

        verticalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                newposition=position;
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        verticalViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x1 = motionEvent.getX();
                        initialX = motionEvent.getX();
                        initialY = motionEvent.getY();

                        if (mContext instanceof MainActivity) {
                            ((MainActivity)mContext).show_toolbar();
                            //Toast.makeText(mContext, "testing", Toast.LENGTH_SHORT).show();
                        }
                        t1.stop();
                        speek.setVisibility(View.VISIBLE);
                        stop.setVisibility(View.GONE);
                        break;
                    case MotionEvent.ACTION_UP:
                        t1.stop();
                        speek.setVisibility(View.VISIBLE);
                        stop.setVisibility(View.GONE);
                        x2 = motionEvent.getX();
                        float deltaX = x1-x2;
                        float delta = x2-x1;
                        float finalX = motionEvent.getX();
                        float finalY = motionEvent.getY();
                        if (initialX < finalX) {
                            if(delta > 300){
                                if (insight_counter % 5 == 0) {
                                    //Toast.makeText(mContext, String.valueOf(insight_counter), Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(mContext, "down", Toast.LENGTH_SHORT).show();
                                    //if(insight_index > 0){
                                    //--insight_index;
                                    //ImageView imageView_insight = (ImageView) itemView.findViewById(R.id.imageView2);
                                    //if (insight_urls.size() == 0 || insight_urls.get(insight_index).equals(null))
                                    //{
                                    //    Picasso.get().load(R.drawable.images).into(imageView_insight);
                                    //}else{
                                    //     Picasso.get().load(insight_urls.get(insight_index))
                                    //           .memoryPolicy(MemoryPolicy.NO_CACHE)
                                    //           .networkPolicy(NetworkPolicy.NO_CACHE)
                                    //           .placeholder(R.drawable.images)
                                    //           .into(imageView_insight);
                                    //}
                                    //    indicator.setSelectedItem(insight_index,true);
                                    //}
                                } else {
                                    // Log.d(TAG, "Left to Right swipe performed");
//                                    Intent intent = new Intent(mContext, Left.class);
//                                    mContext.startActivity(intent);
//                                    Utils.goToFragment(mContext.getApplicationContext(), new MenuFragment(), R.id.activity_feed_polls, true, true);
                                    MainActivity mainActivity = new MainActivity();
                                    mainActivity.go_to_left(mContext);
                                }
                            }
                        }

                        if (initialX > finalX) {
                            if(deltaX > 300) {
                                if (insight_counter % 5 == 0) {
//                                    if(insight_index < (insight_urls.size()-1)){
//                                        Toast.makeText(mContext, String.valueOf(insight_index)+" "+insight_urls.get(insight_index), Toast.LENGTH_SHORT).show();
//                                        ++insight_index;
//                                        ImageView imageView_insight = (ImageView) itemView.findViewById(R.id.imageView2);
//                                        if (insight_urls.size() == 0 || insight_urls.get(insight_index).equals(null))
//                                        {
//                                            Picasso.get().load(R.drawable.images).into(imageView_insight);
//                                        }else{
//                                            Picasso.get().load(insight_urls.get(insight_index))
//                                                    .memoryPolicy(MemoryPolicy.NO_CACHE)
//                                                    .networkPolicy(NetworkPolicy.NO_CACHE)
//                                                    .placeholder(R.drawable.images)
//                                                    .into(imageView_insight);
//                                        }
//                                        indicator.setSelectedItem(insight_index,true);
//                                    }
                                } else {
//                                  Toast.makeText(mContext, "test_1", Toast.LENGTH_SHORT).show();
                                    // Log.d(TAG, "Right to Left swipe performed");
                                    Intent intent = new Intent(mContext, Right.class);
                                    if (position == 1) {
                                        try {
                                            intent.putExtra("weburl", jsonObject.getJSONArray("body").getJSONObject(0).getString("newsurl"));
                                            mContext.startActivity(intent);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        try {
                                            intent.putExtra("weburl", jsonObject.getJSONArray("body").getJSONObject(newposition).getString("newsurl"));
                                            mContext.startActivity(intent);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }

                        if (initialY < finalY) {
                            if(insight_counter > 0){
                                if(oldposisition != newposition && position != 1) {
                                    oldposisition = newposition;
                                    if(delta < 300 && deltaX < 300) {
                                        --insight_counter;
                                    }
                                    if (insight_counter % 5 == 0) {
                                        if(MainActivity.insightList.size() > 0 && insight_index <= MainActivity.insightList.size() ) {
                                            ArrayList<String> url_List1 = MainActivity.insightList.get(insight_index).getIn_data();
                                            insight_slider.setSliderAdapter(new InsightSliderAdapter(mContext, (ArrayList<String>) url_List1));
                                            insight_slider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                                            slider_layout.setVisibility(View.VISIBLE);
                                            main_card_view.setVisibility(View.GONE);
                                            insight_index--;
                                        }
                                    } else {
                                        //                                Toast.makeText(mContext, "test_1", Toast.LENGTH_SHORT).show();
                                        t1.stop();
                                        speek.setVisibility(View.VISIBLE);
                                        stop.setVisibility(View.GONE);
                                        slider_layout.setVisibility(View.GONE);
                                        main_card_view.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                            // Log.d(TAG, "Up to Down swipe performed");
//                            Toast.makeText(mContext, "Down", Toast.LENGTH_SHORT).show();
                        }

                        if (initialY > finalY) {
                            if(oldposisition != newposition) {
                                oldposisition = newposition;
                                if(delta < 300 && deltaX < 300){
                                    ++insight_counter;
                                }
                                if (insight_counter % 5 == 0) {

                                    String insight_urls = "";

                                    if(MainActivity.insightList.size() > 0 && insight_index <= MainActivity.insightList.size() ) {
                                            ArrayList<String> url_List1 = MainActivity.insightList.get(insight_index).getIn_data();
                                            insight_slider.setSliderAdapter(new InsightSliderAdapter(mContext, (ArrayList<String>) url_List1));
                                            insight_slider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                                            slider_layout.setVisibility(View.VISIBLE);
                                            main_card_view.setVisibility(View.GONE);
                                            insight_index++;
                                    }

//                                    Call<Insight> call = MyClientLaravel.getInstance().getLaravelApi().get_single_insight("0");
//                                    call.enqueue(new Callback<Insight>() {
//                                        @Override
//                                        public void onResponse(Call<Insight> call, Response<Insight> response) {
//                                            if (response.isSuccessful()){
////                                                Toast.makeText(mContext, response.body().getResponse(), Toast.LENGTH_SHORT).show();
//                                                if(response.body().getResponse().equals("ok")) {
//                                                    insight_urls = response.body().getIn_data_arr();
//                                                    slider_layout.setVisibility(View.VISIBLE);
//                                                    main_card_view.setVisibility(View.GONE);
//                                                    insight_slider.setSliderAdapter(new InsightSliderAdapter(mContext,insight_urls));
////                                                        insight_slider.setIndicatorAnimation(IndicatorAnimations.WORM);
//                                                    insight_slider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
////                                                    ImageView imageView_insight = (ImageView) itemView.findViewById(R.id.imageView2);
////                                                    if (insight_urls.size() == 0 || insight_urls.get(insight_index).equals(null))
////                                                    {
////                                                        Picasso.get().load(R.drawable.images)
////                                                                .memoryPolicy(MemoryPolicy.NO_CACHE)
////                                                                .networkPolicy(NetworkPolicy.NO_CACHE)
////                                                                .placeholder(R.drawable.images)
////                                                                .into(imageView_insight);
////                                                        indicator.setNumberOfItems(1);
////                                                    }else{
////
////                                                        Picasso.get()
////                                                                .load(insight_urls.get(insight_index))
////                                                                .memoryPolicy(MemoryPolicy.NO_CACHE)
////                                                                .networkPolicy(NetworkPolicy.NO_CACHE)
////                                                                .placeholder(R.drawable.images)
////                                                                .into(imageView_insight);
////                                                        indicator.setNumberOfItems(insight_urls.size());
////                                                    }
////                                                    Toast.makeText(mContext, insight_urls.get(insight_index), Toast.LENGTH_SHORT).show();
//
//                                                }else {
////                                                    Toast.makeText(mContext, "yooooooo", Toast.LENGTH_SHORT).show();
//                                                }
//                                            }else{
//                                                Toast.makeText(mContext,response.errorBody().toString() , Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onFailure(Call<Insight> call, Throwable t) {
////                                            Toast.makeText(mContext, t.toString(), Toast.LENGTH_SHORT).show();
//                                            Log.e("error_insight",t.toString());
//                                            Log.w("error_insight",t.toString());
//                                        }
//                                    });
//                                Toast.makeText(mContext, "up", Toast.LENGTH_SHORT).show();
//                                    slider_layout.setVisibility(View.VISIBLE);
//                                    main_card_view.setVisibility(View.GONE);
                                } else {
//                                Toast.makeText(mContext, "test_1", Toast.LENGTH_SHORT).show();
                                    t1.stop();
                                    speek.setVisibility(View.VISIBLE);
                                    stop.setVisibility(View.GONE);
                                    slider_layout.setVisibility(View.GONE);
                                    main_card_view.setVisibility(View.VISIBLE);
                                }
                            }
//                            Toast.makeText(mContext, "Up", Toast.LENGTH_SHORT).show();
                            // Log.d(TAG, "Down to Up swipe performed");
                        }

//                        x2 = motionEvent.getX();
//                        float deltaX = x1-x2;
//                        float delta = x2-x1;
//                        if(deltaX > 300){
//                            Intent intent = new Intent(mContext,Right.class);
//                            if(position == 1){
//                                try {
//                                    intent.putExtra("weburl",jsonObject.getJSONArray("body").getJSONObject(0).getString("newsurl"));
//                                    mContext.startActivity(intent);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }else{
//                                try {
//                                    intent.putExtra("weburl",jsonObject.getJSONArray("body").getJSONObject(newposition).getString("newsurl"));
//                                    mContext.startActivity(intent);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }else if(delta > 300){
//                            Intent intent = new Intent(mContext,Left.class);
//                            mContext.startActivity(intent);
////                            Toast.makeText(mContext, "right", Toast.LENGTH_SHORT).show();
//                        }else{
////                            ++insight_counter;
////                            if(insight_counter%5 == 0){
////                                Toast.makeText(mContext, "up", Toast.LENGTH_SHORT).show();
////                                slider_layout.setVisibility(View.VISIBLE);
////                                main_card_view.setVisibility(View.GONE);
////                            }else{
//////                                Toast.makeText(mContext, "test_1", Toast.LENGTH_SHORT).show();
////                                slider_layout.setVisibility(View.GONE);
////                                main_card_view.setVisibility(View.VISIBLE);
////                            }
//                        }
                        break;
                }
                return false;
            }
        });

        try {
            String newsImage=jsonObject.getJSONArray("body").getJSONObject(position).getString("imageurl");
            String newsTitle=jsonObject.getJSONArray("body").getJSONObject(position).getString("title");
            String newsDate=jsonObject.getJSONArray("body").getJSONObject(position).getString("date");
            String newsDescription=jsonObject.getJSONArray("body").getJSONObject(position).getString("description");
            String pt="news48";
            String uno = jsonObject.getJSONArray("body").getJSONObject(position).getString("id");
            String newsSrc="news 48 pub";
            String newsAuthor="parshant";
            String newsWebpage=jsonObject.getJSONArray("body").getJSONObject(position).getString("newsurl");
            Log.e("posi",String.valueOf(position));
            String.valueOf(position);
            Log.e("urls",newsWebpage);

            if (newsSrc.equals(null) || newsSrc.equals("null"))
            {
                newsSrc="Unknown";
            }

            if (newsAuthor.equals(null) || newsAuthor.equals("null"))
            {
                newsAuthor="Anonymous";
            }

            if (newsTitle.equals(null) || newsTitle.equals("null"))
            {
                newsTitle="Headline not available";
            }

            if (newsDescription.equals(null) || newsDescription.equals("null"))
            {
                newsDescription="DescriponPausetion not available";
            }


            if (newsImage.equals(null) || newsImage.equals("null"))
            {
                Picasso.get().load(R.drawable.images).into(imageView);

            }

            Picasso.get().load(newsImage).into(imageView);
            label.setText(newsTitle);
            newsDesc.setText(newsDescription);
            web.setText(newsWebpage);


            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    star.setVisibility(View.GONE);
                    sstar.setVisibility(View.VISIBLE);
                    mydb = new DBHelper(mContext);
                    mydb.insertContact(uno);
                    Toast.makeText(mContext, "Added to favorites", Toast.LENGTH_SHORT).show();
                }
            });


            sstar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    star.setVisibility(View.VISIBLE);
                    sstar.setVisibility(View.GONE);
                    mydb = new DBHelper(mContext);
                    mydb.deleteContact(uno);
                    Toast.makeText(mContext, "Removed to favorites", Toast.LENGTH_SHORT).show();
                }
            });

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    like.setVisibility(View.GONE);
                    llike.setVisibility(View.VISIBLE);
                    mydbLike = new DBHelperLike(mContext);
                    mydbLike.insertLike(uno);
                    String abc  = mydbLike.likeDislike("+1");
                    likeUnlike(Integer.valueOf(uno),1);
                    //Toast.makeText(mContext, "Liked "+abc, Toast.LENGTH_SHORT).show();
                }
            });

            speek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    speek.setVisibility(View.GONE);
                    stop.setVisibility(View.VISIBLE);
                    String toSpeak = newsDesc.getText().toString();
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                }
            });

            stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    t1.stop();
                    speek.setVisibility(View.VISIBLE);
                    stop.setVisibility(View.GONE);
                }
            });


            llike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    like.setVisibility(View.VISIBLE);
                    llike.setVisibility(View.GONE);
                    mydbLike = new DBHelperLike(mContext);
                    mydbLike.deleteLike(uno);
                    String abc  = mydbLike.likeDislike("-1");
                    likeUnlike(Integer.valueOf(uno),-1);
//                    Toast.makeText(mContext, "Disliked "+abc, Toast.LENGTH_SHORT).show();
                }
            });

            source.setText("click for more details / "+newsDate);

            mydb = new DBHelper(mContext);
            ArrayList array_list = mydb.getAllCotacts();

            mydbLike = new DBHelperLike(mContext);
            ArrayList array_list_like = mydbLike.getAllLikes();

            String tempString = "";
            for(int x = 0; x < array_list_like.size(); x++) {
                tempString = tempString + ", " + array_list_like.get(x);
            }
            tempString = tempString.substring(2);
//            Toast.makeText(mContext,tempString, Toast.LENGTH_LONG).show();
            Log.e("TAG",tempString);

            for(int x = 0; x < array_list.size(); x++) {
                if (Integer.parseInt(uno) == Integer.parseInt(array_list.get(x).toString())){
                    star.setVisibility(View.GONE);
                    sstar.setVisibility(View.VISIBLE);
                }
            }

            for(int x = 0; x < array_list_like.size(); x++) {
                if (Integer.parseInt(uno) == Integer.parseInt(array_list_like.get(x).toString())){
                    like.setVisibility(View.GONE);
                    llike.setVisibility(View.VISIBLE);
                }
            }

            final String finalNewsSrc = newsWebpage;
            source.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, Right.class);
                    intent.putExtra("weburl", newsWebpage);
                    mContext.startActivity(intent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }


    public static void testing(View view){
//        Log.e("taagg",webu);
        TextView tv = view.findViewById(R.id.textView);
        tv.setVisibility(view.GONE);
//        Log.e("taagg",tv.getText().toString());
    }



    private void shareImage(Uri file){
        Uri uri = file;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, mContext.getString(R.string.app_name));
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "Download "+mContext.getString(R.string.app_name)+" - The #1 News App.\nhttps://play.google.com/store/apps/details?id=com.newsupdates.headlines");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            mContext.startActivity(Intent.createChooser(intent, "Share score"));
        } catch (Exception e) {
            Toast.makeText(mContext, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }

    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public Uri store(Bitmap bm, String fileName){
        Uri bmpUri=null;
        try {
            // This way, you don't need to request external read/write permission.
            File file =  new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "news.png");
            FileOutputStream out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }


    public void likeUnlike (int id , int like){

        Call<pass> call = MyClientLaravel.getInstance().getLaravelApi().likeUnlike(id,like);
        call.enqueue(new Callback<pass>() {
            @Override
            public void onResponse(Call<pass> call, Response<pass> response) {
                String res = response.body().getResponse();
                if (res.isEmpty() || !res.equals("ok")) {
                    Toast.makeText(mContext, res, Toast.LENGTH_SHORT).show();
                    alert(mContext,"News","Please try again after some time",R.color.red);
                }
            }

            @Override
            public void onFailure(Call<pass> call, Throwable t) {
                apifailure(t,mContext);
            }
        });
    }


}
