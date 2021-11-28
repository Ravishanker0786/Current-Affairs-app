package com.prinfosys.news48;

import com.prinfosys.news48.Admin.NewsBody;
import com.prinfosys.news48.Admin.topic.topic;
import com.prinfosys.news48.Model.Quiz;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

//    String main_url = "https://currentaffairs.topprsiq.com/";

    String main_url = "http://prinfosys.co.in/";

    String BASE_URL = main_url+"api/";



    /**
     * The return type is important here
     * The class structure that you've defined in Call<T>
     * should exactly match with your json response
     * If you are not using another api, and using the same as mine
     * then no need to worry, but if you have your own API, make sure
     * you change the return type appropriately
     **/


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
    @POST("quizmaster.php")
    Call<Quiz> quiz(@Field("type") String type,@Field("qid") String qid,
                        @Field("ques") String ques,@Field("ans") String ans,
                          @Field("option1") String option1,@Field("option2") String option2,
                          @Field("option3") String option3,@Field("option4") String option4,@Field("qno") String qno);

    @GET("quizmaster.php")
    Call<Quiz> getQuesList(@Query("type") String type);


    @GET("quizmaster.php")
    Call<Quiz> getQuesListByQno(@Query("type") String type,@Query("qno") String qno);


}