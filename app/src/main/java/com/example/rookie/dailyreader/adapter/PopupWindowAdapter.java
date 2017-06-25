package com.example.rookie.dailyreader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.bean.NewspaperInfo;
import com.example.rookie.dailyreader.db.MypaperDb;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

/**
 * Created by rookie on 2017/6/24.
 */

public class PopupWindowAdapter  extends RecyclerView.Adapter <PopupWindowAdapter.MyViewHolder>{
    private ArrayList<NewspaperInfo> myLists;



    private PopupWindowAdapterOne popupWindowAdapter;
    private Context myContext;
    private NewspaperApater myNewspaperApater;
    public  PopupWindowAdapter(ArrayList<NewspaperInfo> lists,Context context){
        myLists = lists;
        myContext = context;

    }

    public NewspaperApater getMyNewspaperApater() {
        return myNewspaperApater;
    }

    public void setMyNewspaperApater(NewspaperApater myNewspaperApater) {
        this.myNewspaperApater = myNewspaperApater;
    }

    public PopupWindowAdapterOne getPopupWindowAdapter() {
        return popupWindowAdapter;
    }

    public void setPopupWindowAdapter(PopupWindowAdapterOne popupWindowAdapter) {
        this.popupWindowAdapter = popupWindowAdapter;
    }
    public ArrayList<NewspaperInfo> getMyLists() {
        return myLists;
    }

    public void setMyLists(ArrayList<NewspaperInfo> myLists) {
        this.myLists = myLists;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.paper_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final int positionOne = position;
        final NewspaperInfo info = myLists.get(position);
        holder.textView.setText(info.getPaperName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("kkkk", "onClick: "+popupWindowAdapter.getMyLists().size());
                popupWindowAdapter.getMyLists().add(info);
                popupWindowAdapter.notifyDataSetChanged();
                myLists.remove(positionOne);
                notifyItemRemoved(positionOne);
                notifyDataSetChanged();
                myNewspaperApater.getMyLists().remove(positionOne);
                myNewspaperApater.notifyDataSetChanged();
                //数据库中删除
                DataSupport.deleteAll(MypaperDb.class," paperId = ?",info.getPaperId());
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
