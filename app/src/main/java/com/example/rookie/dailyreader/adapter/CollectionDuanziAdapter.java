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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_duanzi_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final  CollectionDuanziDb db = myLists.get(position);
        holder.context.setText(db.getText());
        holder.username.setText(db.getUsername());
        holder.date.setText(DateUtil.getCollctionDate(db.getSaveDate()));
        Glide.with(myContext).load(db.getUsericon()).into(holder.icon);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myLists.remove(position);
                notifyItemRemoved(position);
                DataSupport.deleteAll(CollectionDuanziDb.class,"text = ? and username = ?",db.getText(),db.getUsername());
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
