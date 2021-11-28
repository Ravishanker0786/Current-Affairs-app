package com.prinfosys.news48.Admin;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.prinfosys.news48.Admin.topic.topic;
import com.prinfosys.news48.R;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<topic> {

    LayoutInflater flater;

    public SpinnerAdapter(Activity context, int resouceId, int textviewId, ArrayList<topic> list){

//        super(context,resouceId,textviewId, list);
//        flater = context.getLayoutInflater();
        super(context,resouceId,textviewId,list);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return rowview(convertView,position);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView,position);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private View rowview(View convertView , int position){

//        RowItem rowItem = getItem(position);
        topic top = getItem(position);

        viewHolder holder ;
        View rowview = convertView;
        if (rowview==null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.spinnerview, null, false);

            holder.txtTitle = (TextView) rowview.findViewById(R.id.spinnertext);
            rowview.setTag(holder);
        }else{
            holder = (viewHolder) rowview.getTag();
        }
        holder.txtTitle.setText(top.getTname());

        return rowview;
    }

    private class viewHolder{
        TextView txtTitle;

    }
}

