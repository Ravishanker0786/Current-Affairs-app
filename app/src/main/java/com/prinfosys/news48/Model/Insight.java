package com.prinfosys.news48.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Insight {
    @SerializedName("in_id")
    private String in_id;
    @SerializedName("in_background")
    private String in_background;
    @SerializedName("in_data")
    private ArrayList<String> in_data;
    @SerializedName("in_data_arr")
    private ArrayList<String> in_data_arr;
    @SerializedName("response")
    private String response;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("count")
    private String count;
    @SerializedName("body")
    private ArrayList<Insight> body;

    public String getIn_id() {
        return in_id;
    }

    public String getIn_background() {
        return in_background;
    }

    public ArrayList<String> getIn_data() {
        return in_data;
    }

    public String getResponse() {
        return response;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public ArrayList<Insight> getBody() {
        return body;
    }

    public String getCount() {
        return count;
    }

    public ArrayList<String> getIn_data_arr() {
        return in_data_arr;
    }
}
