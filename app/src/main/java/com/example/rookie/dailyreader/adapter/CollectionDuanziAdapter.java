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
import com.example.rookie.dailyreader.db.CollectionDuanziDb;
import com.example.rookie.dailyreader.util.DateUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by rookie on 2017/6/19.
 * 收藏界面段子模块的适配器
 */

public class CollectionDuanziAdapter extends RecyclerView.Adapter<CollectionDuanziAdapter.MyViewHolder> {
    private List<CollectionDuanziDb> myLists;
    private Context myContext;
    public  CollectionDuanziAdapter(List<CollectionDuanziDb> lists,Context context){
        myLists = lists;
        myContext = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*加载recyclerView的子布局*/
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_duanzi_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,  int position) {
        final  CollectionDuanziDb db = myLists.get(position);
        final  int positionOne = position;
        //段子内容
        holder.context.setText(db.getText());
        //发表用户名
        holder.username.setText(db.getUsername());
        //收藏时间
        holder.date.setText(DateUtil.getCollctionDate(db.getSaveDate()));
        //用户头像
        Glide.with(myContext).load(db.getUsericon()).into(holder.icon);
        //删除键的点击事件，并从数据库中删除对应的数据
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myLists.remove(positionOne);
                notifyItemRemoved(positionOne);
                DataSupport.deleteAll(CollectionDuanziDb.class,"text = ? and username = ?",db.getText(),db.getUsername());
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return myLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView icon;
        TextView username;
        TextView date;
        TextView context;
        ImageView delete;
        public MyViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.collection_duanzi_icon);
            username = (TextView) itemView.findViewById(R.id.collection_duanzi_name);
            date = (TextView) itemView.findViewById(R.id.collection_duanzi_date);
            context = (TextView) itemView.findViewById(R.id.collection_duanzi_context);
            delete = (ImageView) itemView.findViewById(R.id.collection_duanzi_delete);
        }
    }
}
