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
import com.example.rookie.dailyreader.db.CollectionDuanziDb;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by rookie on 2017/5/23.
 * 段子界面的适配器
 */

public class DuanziAdapter extends RecyclerView.Adapter<DuanziAdapter.ViewHolder> {
    private ArrayList<DuanziData> mlist;
    private Context mcontext;
    //段子是否被收藏的标记
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
        //初始化默认为未收藏
        flags = new Boolean[list.size()];
        for (int i = 0; i < list.size(); i++) {
            flags[i] = false;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*加载recyclerView的子布局*/
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.duanzi_layout_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final int clickPosition = position;
        final DuanziData duanziData = mlist.get(position);
        //用户头像
        Glide.with(mcontext).load(duanziData.getUsericon()).into(holder.circleImageView);
        //用户名字
        holder.textViewUserName.setText(duanziData.getUsername());
        //详细内容
        holder.textViewContent.setText(duanziData.getDuanzitext());
        //收藏按钮根据标记的值设置对应的状态
        if (flags[position] == false) {
            holder.collectionImage.setImageResource(R.drawable.news_collection);
        } else {
            holder.collectionImage.setImageResource(R.drawable.collection_selected);
        }
        //收藏按钮的点击事件
        holder.collectionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flags[clickPosition] == false) {
                    //收藏并将详细内容存到数据库
                    holder.collectionImage.setImageResource(R.drawable.collection_selected);
                    flags[clickPosition] = true;
                    CollectionDuanziDb db = new CollectionDuanziDb();
                    db.setSaveDate(new Date());
                    db.setText(duanziData.getDuanzitext());
                    db.setUsername(duanziData.getUsername());
                    db.setUsericon(duanziData.getUsericon());
                    db.save();
                    Toast.makeText(mcontext, "已收藏", Toast.LENGTH_SHORT).show();
                } else {
                    //取消收藏并将对应的信息从数据库中删除
                    holder.collectionImage.setImageResource(R.drawable.news_collection);
                    flags[clickPosition] = false;
                    DataSupport.deleteAll(CollectionDuanziDb.class,"text = ? and username = ?",duanziData.getDuanzitext(),duanziData.getUsername());
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
