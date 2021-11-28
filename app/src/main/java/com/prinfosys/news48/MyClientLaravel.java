package com.prinfosys.news48;

import com.prinfosys.news48.Admin.MyApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyClientLaravel {
    private static MyClientLaravel instance = null;
//    private LaravelApi myApi;
    private LaravelApi LaravelApi;

    private MyClientLaravel() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(LaravelApi.BASE_URL_1)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LaravelApi = retrofit.create(LaravelApi.class);
    }

    public static synchronized MyClientLaravel getInstance() {
        if (instance == null) {
            instance = new MyClientLaravel();
        }
        return instance;
    }

    public LaravelApi getLaravelApi() {
        return LaravelApi;
    }
}
