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
import com.prinfosys.news48.Model.Insight;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class InsightGridviewAdapter extends ArrayAdapter<Insight> {

    ArrayList<Insight> listData;
    Context context;
    int resource;
    public InsightGridviewAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Insight> listData) {
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
            convertView=layoutInflater.inflate(R.layout.insight_list_view,null,true);
        }
        Insight list = getItem(position);
        ImageView img=(ImageView)convertView.findViewById(R.id.img);
        TextView txt = convertView.findViewById(R.id.date);
        txt.setText(list.getCreated_at());
        Picasso.get().load(list.getIn_background()).into(img);
        return convertView;
    }

}
