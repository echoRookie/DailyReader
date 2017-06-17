package com.example.rookie.dailyreader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rookie.dailyreader.R;
import com.example.rookie.dailyreader.activity.NewsDetialActivity;
import com.example.rookie.dailyreader.bean.DuanziData;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by rookie on 2017/5/23.
 */

public class DuanziAdapter extends RecyclerView.Adapter<DuanziAdapter.ViewHolder> {
    private ArrayList<DuanziData> mlist;
    private Context mcontext;

    public Boolean[] getFlags() {
        return flags;
    }

    public void setFlags(Boolean[] flags) {
        this.flags = flags;
    }

    private Boolean[] flags;

    public DuanziAdapter(ArrayList<DuanziData> list, Context context) {
        mcontext = context;
        mlist = list;
        flags = new Boolean[list.size()];
        for (int i = 0; i < list.size(); i++) {
            flags[i] = false;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.duanzi_layout_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final int clickPosition = position;
        DuanziData duanziData = mlist.get(position);
        Glide.with(mcontext).load(duanziData.getUsericon()).into(holder.circleImageView);
        holder.textViewUserName.setText(duanziData.getUsername());
        holder.textViewContent.setText(duanziData.getDuanzitext());
        if (flags[position] == false) {
            holder.collectionImage.setImageResource(R.drawable.news_collection);
        } else {
            holder.collectionImage.setImageResource(R.drawable.github);
        }
        holder.collectionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flags[clickPosition] == false) {
                    holder.collectionImage.setImageResource(R.drawable.github);
                    flags[clickPosition] = true;
                    Toast.makeText(mcontext, "已收藏", Toast.LENGTH_SHORT).show();
                } else {
                    holder.collectionImage.setImageResource(R.drawable.news_collection);
                    flags[clickPosition] = false;
                    Toast.makeText(mcontext, "收藏已取消", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView textViewUserName;
        TextView textViewContent;
        ImageView collectionImage;

        public ViewHolder(View itemView) {
            super(itemView);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.duanzi_user_icon);
            textViewUserName = (TextView) itemView.findViewById(R.id.duanzi_user_name);
            textViewContent = (TextView) itemView.findViewById(R.id.duanzi_content);
            collectionImage = (ImageView) itemView.findViewById(R.id.duanzi_collection);
        }
    }

}
