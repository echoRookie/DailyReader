package com.example.rookie.dailyreader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.bean.NewspaperInfo;
import com.example.rookie.dailyreader.db.MypaperDb;

import java.util.ArrayList;

/**
 * Created by rookie on 2017/6/24.
 *  popupwindow未选择主题日报适配器
 */

public class PopupWindowAdapterOne extends RecyclerView.Adapter <PopupWindowAdapterOne.MyViewHolder>{
    private ArrayList<NewspaperInfo> myLists;
    private Context myContext;
    private PopupWindowAdapter popupWindowAdapter;
    private NewspaperApater myNewspaperApater;
    public PopupWindowAdapterOne(ArrayList<NewspaperInfo> lists, Context context){
        myLists = lists;
        myContext = context;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.paper_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    public PopupWindowAdapter getPopupWindowAdapter() {
        return popupWindowAdapter;
    }

    public void setPopupWindowAdapter(PopupWindowAdapter popupWindowAdapter) {
        this.popupWindowAdapter = popupWindowAdapter;
    }

    public NewspaperApater getMyNewspaperApater() {
        return myNewspaperApater;
    }

    public void setMyNewspaperApater(NewspaperApater myNewspaperApater) {
        this.myNewspaperApater = myNewspaperApater;
    }

    public ArrayList<NewspaperInfo> getMyLists() {
        return myLists;
    }

    public void setMyLists(ArrayList<NewspaperInfo> myLists) {
        this.myLists = myLists;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final int positionOne = position;
        final NewspaperInfo info = myLists.get(position);
        holder.textView.setText(info.getPaperName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //已选择的主题日报添加
                myNewspaperApater.getMyLists().add(info);
                myNewspaperApater.notifyDataSetChanged();
                popupWindowAdapter.getMyLists().add(info);
                popupWindowAdapter.notifyDataSetChanged();
                //添加到数据库
                MypaperDb db = new MypaperDb();
                db.setPaperId(info.getPaperId());
                db.setPaperName(info.getPaperName());
                db.setTitle(info.getTitle());
                db.setImageUrl(info.getImageUrl());
                db.save();
                //未选择的删除
                myLists.remove(positionOne);
                notifyItemRemoved(positionOne);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.paper_item_text);
        }
    }
}
