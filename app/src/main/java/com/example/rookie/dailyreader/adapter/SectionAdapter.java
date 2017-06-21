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
import com.example.rookie.dailyreader.activity.NewsDetialActivity;
import com.example.rookie.dailyreader.bean.SectionInfo;

import java.util.ArrayList;

/**
 * Created by rookie on 2017/6/20.
 * 文章专栏部分的适配器
 */

public class SectionAdapter extends RecyclerView.Adapter <SectionAdapter.MyViewHolder> {
    private ArrayList<SectionInfo> myLists;
    private Context myContext;
    public SectionAdapter(ArrayList<SectionInfo> lists,Context context){
        myLists = lists;
        myContext = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*加载recyclerView的子布局*/
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_section_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final SectionInfo sectionInfo = myLists.get(position);
        //文章发表时间
        holder.date.setText(sectionInfo.getDate());
        //文章标题
        holder.title.setText(sectionInfo.getTitle());
        //文章图片
        Glide.with(myContext).load(sectionInfo.getImageUrl()).into(holder.imageView);
        //文章标题点击跳转到详情页
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myContext, NewsDetialActivity.class);
                intent.putExtra("newsId",sectionInfo.getId());
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
        ImageView imageView;
        TextView date;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.news_section_title);
            imageView = (ImageView) itemView.findViewById(R.id.news_section_image);
            date = (TextView) itemView.findViewById(R.id.news_section_date);
        }
    }
}
