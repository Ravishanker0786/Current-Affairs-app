package com.prinfosys.news48;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//public class InsightSliderAdapter {
//}

public class InsightSliderAdapter extends SliderViewAdapter<InsightSliderAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> list;

    public InsightSliderAdapter(Context context, ArrayList<String> slider) {
        this.context = context;
        this.list = slider;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.insights_slider_view, null);
        return new InsightSliderAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {





        String obj = list.get(position);
        String str = obj.replaceAll("\\[|\\]|\\\\","");
        Log.v("replaced_insight_1",str);
        Picasso.get().load(str).into(viewHolder.imageViewBackground);

//        Glide.with(context).load(str)..into(viewHolder.imageViewBackground);
//        URL newurl = new URL(str);
//        BitmapFactory mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
//        viewHolder.imageViewBackground.setImageBitmap(mIcon_val);
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent it = new Intent(context, TextNews.class);
//                it.putParcelableArrayListExtra("mList", list);
//                it.putExtra("position", position);
//                context.startActivity(it);
//            }
//        });

    }


    @Override
    public int getCount() {
        return list.size();
    }

    public class ViewHolder extends SliderViewAdapter.ViewHolder {
        View itemView;
        ImageView imageViewBackground;
        TextView textViewDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.insight_image_view);
//            textViewDescription = itemView.findViewById(R.id.textViewSlider);
            this.itemView = itemView;
        }
    }
}