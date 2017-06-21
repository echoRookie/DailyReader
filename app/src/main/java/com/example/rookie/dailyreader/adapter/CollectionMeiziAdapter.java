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
import com.example.rookie.dailyreader.activity.MeiziDetialActivity;
import com.example.rookie.dailyreader.activity.NewsDetialActivity;
import com.example.rookie.dailyreader.db.CollectionMeiziDb;
import com.example.rookie.dailyreader.fragment.CollectionMeiziFragment;
import com.example.rookie.dailyreader.util.DateUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by rookie on 2017/6/19.
 * 收藏界面美图模块的适配器
 */

public class CollectionMeiziAdapter extends RecyclerView.Adapter <CollectionMeiziAdapter.MyViewHolder> {
    private List<CollectionMeiziDb> myLists;
    private Context myContext;
    public CollectionMeiziAdapter(List<CollectionMeiziDb> lists,Context context){
        myLists = lists;
        myContext = context;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*加载recyclerView的子布局*/
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_meizi_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //收藏时间
        holder.date.setText(DateUtil.getCollctionDate(myLists.get(position).getSaveDate()));
        //具体图片内容
        Glide.with(myContext).load(myLists.get(position).getIamgeUrl()).into(holder.imageView);
        //删除键的点击事件，并从数据库中删除对应的数据
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myLists.remove(position);
                DataSupport.deleteAll(CollectionMeiziDb.class,"iamgeUrl = ?",myLists.get(position).getIamgeUrl());
                notifyItemRemoved(position);
            }
        });
        //图片点击跳转到详情页
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(myContext, MeiziDetialActivity.class);
                intent.putExtra("imageUrl",myLists.get(position).getIamgeUrl());
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
        TextView date;
        ImageView imageView;
        ImageView delete;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.collection_meizi_image);
            date = (TextView) itemView.findViewById(R.id.collection_meizi_date);
            delete = (ImageView) itemView.findViewById(R.id.collection_meizi_delete);
        }
    }
}
