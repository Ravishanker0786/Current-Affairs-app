package com.prinfosys.news48.Model;

import com.google.gson.annotations.SerializedName;

public class pass {

    @SerializedName("response")
    private String response;
    @SerializedName("pass")
    private String pass;

    public String getResponse() {
        return response;
    }

    public String getPass() {
        return pass;
    }
}
