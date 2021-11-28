package com.prinfosys.news48.Admin.topic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.prinfosys.news48.R;
import com.prinfosys.news48.Admin.NewsBody;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopicAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<topic> topiclist;

    public TopicAdapter(Context context, ArrayList<topic> topiclist) {
        this.context = context;
        this.topiclist = topiclist;
    }

    @Override
    public int getCount() {
        return topiclist.size();
    }

    @Override
    public Object getItem(int i) {
        return topiclist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.topicview,viewGroup,false);
        }
        topic t = (topic) getItem(i);
        TextView sr = view.findViewById(R.id.sr);
        TextView tname = view.findViewById(R.id.tname);
        ImageView img = view.findViewById(R.id.img);

        Picasso.get().load(t.getUrl()).into(img);
        sr.setText(String.valueOf(i+1));
        tname.setText(t.getTname());
        return view;
    }
}
