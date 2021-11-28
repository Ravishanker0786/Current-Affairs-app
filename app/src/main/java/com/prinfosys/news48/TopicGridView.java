package com.prinfosys.news48;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.prinfosys.news48.Admin.topic.topic;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TopicGridView extends ArrayAdapter<topic> {

    ArrayList<topic> listData;
    Context context;
    int resource;

    public TopicGridView(@NonNull Context context, int resource, @NonNull ArrayList<topic> listData) {
        super(context, resource, listData);

        this.context=context;
        this.resource=resource;
        this.listData=listData;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView==null){
            LayoutInflater layoutInflater=(LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.gridview,null,true);
        }
        topic list = getItem(position);
        ImageView img=(ImageView)convertView.findViewById(R.id.topicimg);
        TextView txt = convertView.findViewById(R.id.topictxt);
        txt.setText(list.getTname());
        Picasso.get().load(list.getUrl()).into(img);

        return convertView;
    }

}
