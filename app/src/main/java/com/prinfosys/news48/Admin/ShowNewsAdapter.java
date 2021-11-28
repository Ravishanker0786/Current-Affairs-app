package com.prinfosys.news48.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.prinfosys.news48.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShowNewsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NewsBody> newslist;

    public ShowNewsAdapter(Context context, ArrayList<NewsBody> newslist) {
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
            view = LayoutInflater.from(context).inflate(R.layout.newsshow, viewGroup,false);
        }
        NewsBody bdy = (NewsBody) getItem(i);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView desc = (TextView) view.findViewById(R.id.descrip);
        TextView newsurl = (TextView) view.findViewById(R.id.newsurl);
        ImageView img = (ImageView) view.findViewById(R.id.img);

        title.setText(bdy.getTitle());
        desc.setText(bdy.getDescription());
        newsurl.setText(bdy.getNewsurl());
        Picasso.get().load(bdy.getImageurl()).into(img);
        return view;
    }
}
