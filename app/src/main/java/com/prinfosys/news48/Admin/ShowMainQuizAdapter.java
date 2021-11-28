package com.prinfosys.news48.Admin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.prinfosys.news48.Model.Quiz;
import com.prinfosys.news48.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShowMainQuizAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Quiz> newslist;

    public ShowMainQuizAdapter(Context context, ArrayList<Quiz> newslist) {
        this.context = context;
        this.newslist = newslist;
    }

    @Override
    public int getCount() {
        return newslist.size();
    }

    @Override
    public Object getItem(int i) {
        return newslist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.quizmainshow, viewGroup,false);
        }
        Toast.makeText(context, "hllo", Toast.LENGTH_SHORT).show();
        Quiz bdy = (Quiz) getItem(i);
        TextView title = (TextView) view.findViewById(R.id.sr_quiz);
        TextView desc = (TextView) view.findViewById(R.id.date);


        title.setText(bdy.getQno());
        desc.setText(bdy.getQuiz_timestamp());
        return view;
    }
}
