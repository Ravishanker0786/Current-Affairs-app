package com.prinfosys.news48.Model;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Quiz {
    @SerializedName("qid")
    private String qid;
    @SerializedName("qno")
    private String qno;
    @SerializedName("ques")
    private String ques;
    @SerializedName("ans")
    private String ans;
    @SerializedName("option1")
    private String option1;
    @SerializedName("option2")
    private String option2;
    @SerializedName("option3")
    private String option3;
    @SerializedName("option4")
    private String option4;
    @SerializedName("response")
    private String response;
    @SerializedName("quiz_timestamp")
    private String quiz_timestamp;
    @SerializedName("body")
    private ArrayList<Quiz> body;

    public String getQid() {
        return qid;
    }

    public String getQno() {
        return qno;
    }

    public ArrayList<Quiz> getBody() {
        return body;
    }

    public String getResponse() {
        return response;
    }

    public String getQues() {
        return ques;
    }

    public String getAns() {
        return ans;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getQuiz_timestamp() {
        return quiz_timestamp;
    }
}
