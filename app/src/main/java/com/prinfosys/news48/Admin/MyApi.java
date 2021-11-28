package com.prinfosys.news48.Admin;

import com.prinfosys.news48.Admin.topic.topic;
import com.prinfosys.news48.Model.pass;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyApi {

    @FormUrlEncoded
    @POST("addnews.php")
    Call<ResponseBody> insertdata(
            @Field("type")String type,
            @Field("title")String title,
            @Field("imageurl")String imageurl,
            @Field("newsurl")String newsurl,
            @Field("description")String description,
            @Field("tid")String tid
            );

    @FormUrlEncoded
    @POST("topic.php")
    Call<topic> inserttopic(
            @Field("type")String type,
            @Field("tname")String tname,
            @Field("url")String url
    );

    @FormUrlEncoded
    @POST("topic.php")
    Call<topic> Updatetopic(
            @Field("type")String type,
            @Field("tname")String tname,
            @Field("id")String id,
            @Field("url")String url
    );

    @FormUrlEncoded
    @POST("addnews.php")
    Call<ResponseBody> updatedata(
            @Field("id")String id,
            @Field("type")String type,
            @Field("title")String title,
            @Field("imageurl")String imageurl,
            @Field("newsurl")String newsurl,
            @Field("description")String description,
            @Field("tid")String tid
    );

    @GET("shownews.php") //  the string in the GET is end part of the endpoint url
    public Call<NewsBody> newsdata(@Query("type") String type);


    @GET("topic.php") //  the string in the GET is end part of the endpoint url
    public Call<topic> gettopic(@Query("type") String type);

    @GET("shownews.php") //  the string in the GET is end part of the endpoint url
    public Call<pass> getpass(@Query("type") String type);
}
