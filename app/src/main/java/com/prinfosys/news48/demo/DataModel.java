package com.prinfosys.news48.demo;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import kotlin.text.UStringsKt;

/**
 * Created by anupamchugh on 28/02/18.
 */

public class DataModel {

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("imageurl")
    private  String imageurl;
    @SerializedName("newsurl")
    private String newsurl;
    @SerializedName("description")
    private String description;
    @SerializedName("response")
    private String response;
    @SerializedName("body")
    private ArrayList<DataModel> body;

    public DataModel(String title, String imageurl, String newsurl, String description) {
        this.title = title;
        this.imageurl = imageurl;
        this.newsurl = newsurl;
        this.description = description;
    }

    public DataModel(String title, String newsurl, String description) {
        this.title = title;
        this.newsurl = newsurl;
        this.description = description;
    }

    public ArrayList<DataModel> getBody() {
        return body;
    }

    public String getResponse() {
        return response;
    }

    public String getId() {
        return id;
    }

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
}
