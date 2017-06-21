package com.example.rookie.dailyreader.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.activity.MeiziDetialActivity;
import com.example.rookie.dailyreader.activity.NewsDetialActivity;
import com.example.rookie.dailyreader.db.CollectionNewsDb;
import com.example.rookie.dailyreader.util.DateUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by rookie on 2017/6/19.
 */

public class CollectionNewAdapter extends RecyclerView.Adapter<CollectionNewAdapter.MyViewHolder> {
    private List<CollectionNewsDb> myLists;
    private Context myContext;
    public CollectionNewAdapter(List<CollectionNewsDb> lists,Context context){
        myLists = lists;
        myContext = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*加载recyclerView的子布局*/
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_new_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final CollectionNewsDb collectionNewsDb = myLists.get(position);
        //收藏时间
        holder.date.setText(DateUtil.getCollctionDate(collectionNewsDb.getSaveDate()));
        //文章标题
        holder.title.setText(collectionNewsDb.getTitle());
        //删除键的点击事件，并从数据库中删除对应的数据
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(CollectionNewsDb.class,"newsId = ?",collectionNewsDb.getNewsId());
                myLists.remove(position);
                notifyItemRemoved(position);
            }
        });
        //标题点击跳转到详情页
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(myContext, NewsDetialActivity.class);
                intent.putExtra("newsId",collectionNewsDb.getNewsId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                myContext.startActivity(intent);
            }
        });
        //加载标题对应的图片
        Glide.with(myContext).load(collectionNewsDb.getImageUrl()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return myLists.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView title;
        ImageView image;
        ImageView delete;
        public MyViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.collection_news_date);
            title = (TextView) itemView.findViewById(R.id.collection_news_title);
            image = (ImageView) itemView.findViewById(R.id.collection_news_image);
            delete = (ImageView) itemView.findViewById(R.id.collection_news_delete);
        }
    }
}
