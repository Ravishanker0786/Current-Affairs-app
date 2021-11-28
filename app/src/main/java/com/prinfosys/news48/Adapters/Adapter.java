package com.prinfosys.news48.Adapters;

import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;

import com.prinfosys.news48.Model.Quiz;
import com.prinfosys.news48.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends PagerAdapter {

    private ArrayList<Quiz> models;
    private LayoutInflater layoutInflater;
    private Context context;
    private RadioGroup rg;
    private String ans = "h",pos="0",ca;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    public Adapter(ArrayList<Quiz> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem( ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.fragment_quiz, container, false);
        Integer[] colors_temp = {
                context.getResources().getColor(R.color.color1),
                context.getResources().getColor(R.color.color2),
                context.getResources().getColor(R.color.color3),
                context.getResources().getColor(R.color.color4)
        };

//        ImageView imageView;
        LinearLayout l1;
        TextView ques;
        RadioButton o1,o2,o3,o4;
        Button sub;

        ques = view.findViewById(R.id.question_quiz);
        o1=view.findViewById(R.id.radio_ans1);
        o2=view.findViewById(R.id.radio_ans2);
        o3=view.findViewById(R.id.radio_ans3);
        o4=view.findViewById(R.id.radio_ans4);
        rg = view.findViewById(R.id.radioGroup);
        l1 = view.findViewById(R.id.l1);
        sub = view.findViewById(R.id.submit_ques);
        sub.setBackgroundColor((Integer)argbEvaluator.evaluate(position+1, colors_temp[position % colors_temp.length], colors_temp[(position + 1) %colors_temp.length]));
        l1.setBackgroundColor((Integer)argbEvaluator.evaluate(position+1, colors_temp[position % colors_temp.length], colors_temp[(position + 1) %colors_temp.length]));
        ques.setText(models.get(position).getQues());
        o1.setText("  "+models.get(position).getOption1());
        o2.setText("  "+models.get(position).getOption2());
        o3.setText("  "+models.get(position).getOption3());
        o4.setText("  "+models.get(position).getOption4());


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radio_ans1:
                        // do operations specific to this selection
                        ans =o1.getText().toString();
                        pos ="1";
                        break;
                    case R.id.radio_ans2:
                        // do operations specific to this selection
                        ans = o2.getText().toString();
                        pos = "2";
                        break;
                    case R.id.radio_ans3:
                        // do operations specific to this selection
                        ans = o3.getText().toString();
                        pos = "3";
                        break;
                    case R.id.radio_ans4:
                        // do operations specific to this selection
                        ans = o4.getText().toString();
                        pos = "4";
                        break;
                }
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, ans, Toast.LENGTH_SHORT).show();
                if(models.get(position).getOption1().equals(models.get(position).getAns())){
                    ca="1";
                }else if(models.get(position).getOption2().equals(models.get(position).getAns())){
                    ca = "2";
                }else if(models.get(position).getOption3().equals(models.get(position).getAns())){
                    ca = "3";
                }
                o1.setBackgroundColor(Color.parseColor("#ffffff"));
                o1.setBackgroundColor(Color.parseColor("#ffffff"));
                o1.setBackgroundColor(Color.parseColor("#ffffff"));
                if(pos.equals("1")){
                    if(ans.trim().equals(models.get(position).getAns())){
//                        Toast.makeText(context, "hllo", Toast.LENGTH_SHORT).show();
                        o1.setBackgroundColor(Color.parseColor("#00ff00"));
                    }else{
                        o1.setBackgroundColor(Color.parseColor("#ff0000"));
                    }
                }else if(pos.equals("2")){
                    if(ans.trim().equals(models.get(position).getAns())){
                        o2.setBackgroundColor(Color.parseColor("#00ff00"));
                    }else{
                        o2.setBackgroundColor(Color.parseColor("#ff0000"));
                    }
                }if (pos.equals("3")){
                    if(ans.trim().equals(models.get(position).getAns())){
                        o3.setBackgroundColor(Color.parseColor("#00ff00"));
                    }else{
                        o3.setBackgroundColor(Color.parseColor("#ff0000"));
                    }
                }
                Toast.makeText(context, ca, Toast.LENGTH_SHORT).show();
                if (ca.equals("1")){
                    o1.setBackgroundColor(Color.parseColor("#00ff00"));
                }else if (ca.equals("2")){
                    o2.setBackgroundColor(Color.parseColor("#00ff00"));
                }else if (ca.equals("3")){
                    o3.setBackgroundColor(Color.parseColor("#00ff00"));
                }



            }
        });

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, DetailActivity.class);
//                intent.putExtra("param", models.get(position).getTitle());
//                context.startActivity(intent);
//                // finish();
//            }
//        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem( ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
