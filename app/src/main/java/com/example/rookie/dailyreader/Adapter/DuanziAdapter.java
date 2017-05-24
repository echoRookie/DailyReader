package com.example.rookie.dailyreader.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.util.DuanziData;
import com.example.rookie.dailyreader.util.DuanziInfo;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by rookie on 2017/5/23.
 */

public class DuanziAdapter extends RecyclerView.Adapter<DuanziAdapter.ViewHolder> {
    private ArrayList<DuanziData> mlist;
    private Context mcontext;
    public DuanziAdapter(ArrayList<DuanziData> list,Context context){
          mcontext=context;
          mlist=list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.duanzi_layout_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DuanziData duanziData = mlist.get(position);
        Glide.with(mcontext).load(duanziData.getUsericon()).into(holder.circleImageView);
        holder.textViewUserName.setText(duanziData.getUsername());
        holder.textViewContent.setText(duanziData.getDuanzitext());

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView textViewUserName;
        TextView textViewContent;

        public ViewHolder(View itemView) {
            super(itemView);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.duanzi_user_icon);
            textViewUserName = (TextView) itemView.findViewById(R.id.duanzi_user_name);
            textViewContent = (TextView) itemView.findViewById(R.id.duanzi_content);
        }
    }

}
