package com.prinfosys.news48.Admin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseBody {

    @SerializedName("response")
    @Expose
    private String response;

    public String getResponse() {
        return response;
    }
}
