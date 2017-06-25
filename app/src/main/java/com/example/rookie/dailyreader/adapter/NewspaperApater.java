package com.example.rookie.dailyreader.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.activity.NewspaperItemActivity;
import com.example.rookie.dailyreader.bean.NewspaperInfo;

import java.util.ArrayList;

/**
 * Created by rookie on 2017/6/24.
 */

public class NewspaperApater extends RecyclerView.Adapter <NewspaperApater.MyViewHolder> {
    private ArrayList<NewspaperInfo> myLists;
    private Context myContext;
    public  NewspaperApater(ArrayList<NewspaperInfo> lists,Context context){
        myLists = lists;
        myContext = context;

    }

    public ArrayList<NewspaperInfo> getMyLists() {
        return myLists;
    }

    public void setMyLists(ArrayList<NewspaperInfo> myLists) {
        this.myLists = myLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_section_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final NewspaperInfo info = myLists.get(position);
        holder.title.setText(info.getTitle());
        holder.paperName.setText(info.getPaperName());
        Glide.with(myContext).load(info.getImageUrl()).into(holder.imageView);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myContext, NewspaperItemActivity.class);
                intent.putExtra("paperId",info.getPaperId());
                intent.putExtra("paperName",info.getPaperName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView paperName;
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.news_section_title);
            paperName = (TextView) itemView.findViewById(R.id.news_section_date);
            imageView = (ImageView) itemView.findViewById(R.id.news_section_image);
        }
    }
}
