package com.prinfosys.news48.Admin;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NewsBody {

    @SerializedName("title")
    private String title;

    @SerializedName("response")
    private String response;

    @SerializedName("imageurl")
    private String imageurl;

    @SerializedName("newsurl")
    private String newsurl;

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private String id;

    @SerializedName("tid")
    private String tid;

    @SerializedName("like")
    private String like;

    @SerializedName("body")
    private ArrayList<NewsBody> newsList;

    public String getTitle() {
        return title;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getNewsurl() {
        return newsurl;
    }

    public String getDescription() {
        return description;
    }

    public String getResponse() {
        return response;
    }

    public ArrayList<NewsBody> getNewsList() {
        return newsList;
    }

    public String getId() {
        return id;
    }

    public String getTid() {
        return tid;
    }

    public String getLike() {
        return like;
    }

}
