package com.example.rookie.dailyreader.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.gson.MeiziGson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rookie on 2017/5/17.
 */

public class MeiziAdapter extends RecyclerView.Adapter<MeiziAdapter.ViewHolder> {
    private ArrayList<String> mlist;
    private Context context;
    static  class  ViewHolder extends  RecyclerView.ViewHolder{
        ImageView MeiziImage;

        public ViewHolder(View itemView) {
            super(itemView);
            MeiziImage = (ImageView)  itemView.findViewById(R.id.meizi_item_image);
        }
    }
    public  MeiziAdapter (ArrayList <String> list){
        mlist=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meizi_layout_item,parent,false);
        ViewHolder  holder= new ViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(mlist.get(position)).into(holder.MeiziImage);

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }


}
