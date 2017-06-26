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
import com.example.rookie.dailyreader.bean.ViewPagerItem;

import java.util.ArrayList;

/**
 * Created by rookie on 2017/6/25.
 * 某个主题日报下文章数据的适配器
 */

public class NewspaperItemAdapter extends RecyclerView.Adapter<NewspaperItemAdapter.MyViewHolder> {
    private ArrayList<ViewPagerItem> myLists;
    private Context myContext;
    public NewspaperItemAdapter(ArrayList<ViewPagerItem> lists,Context context){
        myLists = lists;
        myContext = context;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_recyclerview_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ViewPagerItem viewPagerItem = myLists.get(position);
        holder.textView.setText(viewPagerItem.getTitle());
        //判断返回数据是否有图片，没有则不显示
        if(myLists.get(position).getImageUrl() == null){
            holder.imageView.setVisibility(View.GONE);
        }
        else {
            Glide.with(myContext).load(viewPagerItem.getImageUrl()).into(holder.imageView);
        }
        //文章标题点击跳转到文章详情页
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myContext, NewsDetialActivity.class);
                intent.putExtra("newsId",viewPagerItem.getId());
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
        ImageView imageView;
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.news_recycler_image);
            textView = (TextView) itemView.findViewById(R.id.news_recycler_title);
        }
    }
}
