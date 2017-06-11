package com.example.rookie.dailyreader.adapter;

import android.content.Context;
import android.content.Intent;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.activity.MeiziDetialActivity;

import java.util.ArrayList;

/**
 * Created by rookie on 2017/5/17.
 */

public class MeiziAdapter extends RecyclerView.Adapter<MeiziAdapter.ViewHolder> {
    private ArrayList<String> mlist;
    private Context mcontext;
    static  class  ViewHolder extends  RecyclerView.ViewHolder{
        ImageView MeiziImage;

        public ViewHolder(View itemView) {
            super(itemView);
            MeiziImage = (ImageView)  itemView.findViewById(R.id.meizi_item_image);
        }
    }
    public  MeiziAdapter (ArrayList <String> list,Context context){
        mlist=list;
        mcontext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meizi_layout_item,parent,false);
        ViewHolder  holder= new ViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(mcontext).load(mlist.get(position)).into(holder.MeiziImage);
        holder.MeiziImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(mcontext, MeiziDetialActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("mlist",mlist);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }


}