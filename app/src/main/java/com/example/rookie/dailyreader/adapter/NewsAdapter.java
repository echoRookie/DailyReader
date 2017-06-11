package com.example.rookie.dailyreader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.bean.ViewPagerItem;

import java.util.ArrayList;

/**
 * Created by rookie on 2017/6/11.
 */

public class NewsAdapter extends RecyclerView.Adapter <NewsAdapter.MyViewHolder>{
    private Context myContext;
    private ArrayList<ViewPagerItem> myList;
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
        ViewPagerItem viewPagerItem = myList.get(position);
        holder.textView.setText(viewPagerItem.getTitle());
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