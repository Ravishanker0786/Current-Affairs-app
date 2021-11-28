package com.prinfosys.news48.Admin.Insight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.prinfosys.news48.Admin.NewsBody;
import com.prinfosys.news48.Model.Insight;
import com.prinfosys.news48.Model.Quiz;
import com.prinfosys.news48.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InsightAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<Insight> insightlist;

    public InsightAdapter(Context context, ArrayList<Insight> insightlist) {
        this.context = context;
        this.insightlist = insightlist;
    }


    @Override
    public int getCount() {
        return insightlist.size();
    }

    @Override
    public Object getItem(int position) {
        return insightlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.insight_list_view, parent,false);
        }

        Insight bdy = (Insight) getItem(position);
        TextView sr = (TextView) convertView.findViewById(R.id.sr);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        ImageView img = (ImageView) convertView.findViewById(R.id.img);

        sr.setText(String.valueOf(position + 1));
        date.setText(bdy.getCreated_at());
        Picasso.get().load(bdy.getIn_background()).into(img);

        return convertView;
    }
}
