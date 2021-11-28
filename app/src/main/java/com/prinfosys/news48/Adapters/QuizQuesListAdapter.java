package com.prinfosys.news48.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.prinfosys.news48.Model.Quiz;
import com.prinfosys.news48.R;

import java.util.ArrayList;

public class QuizQuesListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Quiz> quizList;

    public QuizQuesListAdapter(Context context, ArrayList<Quiz> quizList) {
        this.context = context;
        this.quizList = quizList;
    }

    @Override
    public int getCount() {
        return quizList.size();
    }

    @Override
    public Object getItem(int i) {
        return quizList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.ques_list,viewGroup,false);
        }
        TextView sr = view.findViewById(R.id.sr_queslist);
        TextView header = view.findViewById(R.id.head_queslist);
        Quiz quiz = (Quiz) getItem(i);
        sr.setText(String.valueOf(i+1));
        header.setText(quiz.getQues());
        return view;
    }
}
