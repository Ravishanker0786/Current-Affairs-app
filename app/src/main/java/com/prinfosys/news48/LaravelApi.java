package com.prinfosys.news48;

import com.prinfosys.news48.Admin.topic.topic;
import com.prinfosys.news48.Model.Insight;
import com.prinfosys.news48.Model.Login;
import com.prinfosys.news48.Model.Quiz;

import com.prinfosys.news48.Model.pass;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LaravelApi {
    String BASE_URL_1 = "http://prinfosys.co.in/currentaffair/api/";


    @GET("quiz/get") //  the string in the GET is end part of the endpoint url
    Call<Quiz> getquiz();

    @GET("insight/get") //  the string in the GET is end part of the endpoint url
    Call<Insight> getinsight();

    @GET("insight/get_single") //  the string in the GET is end part of the endpoint url
    Call<Insight> get_single_insight(@Query("id") String id);

    @FormUrlEncoded
    @POST("news/like")
    Call<pass> likeUnlike(
            @Field("id")int id,
            @Field("like")int like
    );

    @FormUrlEncoded
    @POST("socialLogin")
    Call<Login>socialLogin(
            @Field("email") String email,
            @Field("name") String name
    );


}
