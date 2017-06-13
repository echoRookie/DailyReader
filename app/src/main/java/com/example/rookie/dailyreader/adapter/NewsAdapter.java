package com.example.rookie.dailyreader.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.activity.NewsDetialActivity;
import com.example.rookie.dailyreader.bean.ViewPagerItem;
import com.example.rookie.dailyreader.gson.NewsInfoGson;
import com.example.rookie.dailyreader.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by rookie on 2017/6/11.
 */

public class NewsAdapter extends RecyclerView.Adapter <NewsAdapter.MyViewHolder>{
    private Context myContext;
    private ArrayList<ViewPagerItem> myList;
    private String newsData;
    public NewsAdapter(Context context,ArrayList<ViewPagerItem> list){
        myContext = context;
        myList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_recyclerview_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return  myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ViewPagerItem viewPagerItem = myList.get(position);

        holder.textView.setText(viewPagerItem.getTitle());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newsId = viewPagerItem.getId();
                Log.d("ooo", "onClick: "+newsId);
                Intent intent = new Intent(myContext, NewsDetialActivity.class);
                intent.putExtra("newsId",newsId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myContext.startActivity(intent);
            }
        });
        Glide.with(myContext).load(viewPagerItem.getImageUrl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    static class MyViewHolder  extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.news_recycler_image);
            textView = (TextView) itemView.findViewById(R.id.news_recycler_title);
        }
    }
}
