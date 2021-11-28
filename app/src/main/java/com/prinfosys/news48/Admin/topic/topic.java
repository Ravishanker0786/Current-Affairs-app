package com.prinfosys.news48.Admin.topic;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class topic {
    @SerializedName("tname")
    private String tname;

    @SerializedName("url")
    private String url;

    @SerializedName("id")
    private String id;

    @SerializedName("response")
    private String response;

    @SerializedName("body")
    private ArrayList<topic> topicList;

    public String getTname() {
        return tname;
    }

    public String getId() {
        return id;
    }

    public String getResponse() {
        return response;
    }

    public ArrayList<topic> getTopicList() {
        return topicList;
    }

    public String getUrl() {
        return url;
    }
}
